using System;
using System.Collections.Generic;
using System.Drawing;
using System.Globalization;
using System.Threading;
using System.Windows.Forms;
using SalesmanGA.Common;
using SalesmanGA.Event_Args;
using SalesmanGA.Helpers;
using SalesmanGA.Models;

namespace SalesmanGA
{


    /// <summary>
    /// Main form for the Travelling Salesman Problem
    /// </summary>
    public partial class SalesmanGaForm : Form
    {
        #region Constructors

        /// <summary>
        /// Default constructor.
        /// </summary>
        public SalesmanGaForm()
        {
            InitializeComponent();
        }

        #endregion

        #region Delegates

        /// <summary>
        /// Delegate for the thread that runs the TSP algorithm.
        /// We use a separate thread so the GUI can redraw as the algorithm runs.
        /// </summary>
        /// <param name="sender">Object that generated this event.</param>
        /// <param name="e">Event arguments.</param>
        public delegate void DrawEventHandler(Object sender, SalesmanEventArgs e);

        #endregion

        #region Public properties

        /// <summary>
        /// The list of cities where we are trying to find the best tour.
        /// </summary>
        public List<City> CityList { get; set; } = new List<City>();

        /// <summary>
        /// The class that does all the work in the TSP algorithm.
        /// If this is not null, then the algorithm is still running.
        /// </summary>
        public SalesmanBrains Salesman { get; set; }

        /// <summary>
        /// The image that we draw the tour on.
        /// </summary>
        public Image CityImage { get; set; }

        /// <summary>
        /// The graphics object for the image that we draw the tour on.
        /// </summary>
        public Graphics CityGraphics { get; set; }

        #endregion

        #region Private methods

        /// <summary>
        /// Handles the foundNewBestTour event of the tsp control.
        /// </summary>
        /// <param name="sender">The source of the event.</param>
        /// <param name="e">The <see cref="SalesmanEventArgs"/> instance containing the event data.</param>
        private void salesman_foundNewBestTour(object sender, SalesmanEventArgs e)
        {
            if (InvokeRequired)
            {
                try
                {
                    Invoke(new DrawEventHandler(DrawRoute), sender, e);
                    return;
                }
                catch (Exception)
                {
                    // ignored
                }
            }

            DrawRoute(sender, e);
        }

        /// <summary>
        /// Draws the route.
        /// </summary>
        /// <param name="sender">The sender.</param>
        /// <param name="e">The <see cref="SalesmanEventArgs"/> instance containing the event data.</param>
        private void DrawRoute(object sender, SalesmanEventArgs e)
        {
            lastFitnessValue.Text = Math.Round(e.BestTour.Fitness, 2).ToString(CultureInfo.CurrentCulture);
            lastIterationValue.Text = e.Generation.ToString(CultureInfo.CurrentCulture);

            if (CityImage == null)
            {
                CityImage = new Bitmap(tourDiagram.Width, tourDiagram.Height);
                CityGraphics = Graphics.FromImage(CityImage);
            }

            int lastCity = 0;
            int nextCity = e.BestTour[0].Connection1;

            CityGraphics.FillRectangle(Brushes.White, 0, 0, CityImage.Width, CityImage.Height);
            foreach (City city in e.CityList)
            {
                // Draw a circle for the city.
                CityGraphics.DrawEllipse(Pens.Black, city.Location.X - 2, city.Location.Y - 2, 5, 5);

                // Draw the line connecting the city.
                CityGraphics.DrawLine(Pens.Black, CityList[lastCity].Location, CityList[nextCity].Location);

                // figure out if the next city in the list is [0] or [1]
                if (lastCity != e.BestTour[nextCity].Connection1)
                {
                    lastCity = nextCity;
                    nextCity = e.BestTour[nextCity].Connection1;
                }
                else
                {
                    lastCity = nextCity;
                    nextCity = e.BestTour[nextCity].Connection2;
                }
            }

            tourDiagram.Image = CityImage;

            if (e.Complete)
            {
                StartButton.Text = "Begin";
                StatusLabel.Text = "Click on the mapto place cities";
                StatusLabel.ForeColor = Color.Black;
            }
        }

        /// <summary>
        /// Draw just the list of cities.
        /// </summary>
        /// <param name="cityList">The list of cities to draw.</param>
        private void DrawCityList(List<City> cityList)
        {
            Image cityImage = new Bitmap(tourDiagram.Width, tourDiagram.Height);
            Graphics graphics = Graphics.FromImage(cityImage);

            foreach (City city in cityList)
            {
                // Draw a circle for the city.
                graphics.DrawEllipse(Pens.Black, city.Location.X - 2, city.Location.Y - 2, 5, 5);
            }

            tourDiagram.Image = cityImage;

            UpdateCityCount();
        }

        /// <summary>
        /// Handles the Click event of the StartButton control.
        /// </summary>
        /// <param name="sender">The source of the event.</param>
        /// <param name="e">The <see cref="EventArgs"/> instance containing the event data.</param>
        private void StartButton_Click(object sender, EventArgs e)
        {
            // we are already running, so tell the tsp thread to halt.
            if (Salesman != null)
            {
                Salesman.Halt = true;
                return;
            }

            int populationSize = 0;
            int maxGenerations = 0;
            int mutation = 0;

            try
            {
                populationSize = Convert.ToInt32(populationSizeTextBox.Text);
                maxGenerations = Convert.ToInt32(maxGenerationTextBox.Text);
                mutation = Convert.ToInt32(mutationTextBox.Text);
            }
            catch (FormatException)
            {
            }

            if (populationSize <= 0)
            {
                MessageBox.Show(ExceptionConstants.POPULATION_ERROR, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error, MessageBoxDefaultButton.Button1, MessageBoxOptions.DefaultDesktopOnly);
                return;
            }
            if (maxGenerations <= 0)
            {
                MessageBox.Show(ExceptionConstants.MAX_GENERATION_ERROR, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error, MessageBoxDefaultButton.Button1, MessageBoxOptions.DefaultDesktopOnly);
                return;
            }
            if ((mutation < 0) || (mutation > 100))
            {
                MessageBox.Show(ExceptionConstants.MUTATION_PERCENTAGE_ERROR, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error, MessageBoxDefaultButton.Button1, MessageBoxOptions.DefaultDesktopOnly);
                return;
            }

            if (CityList.Count < 5)
            {
                MessageBox.Show(ExceptionConstants.MIN_CITY_COUNT_ERROR, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error, MessageBoxDefaultButton.Button1, MessageBoxOptions.DefaultDesktopOnly);
                return;
            }

            // ReSharper disable once LocalizableElement
            StartButton.Text = "Stop";
            ThreadPool.QueueUserWorkItem(BeginSalesmanTrip);
        }

        /// <summary>
        /// Begins the salesman trip.
        /// </summary>
        /// <param name="stateInfo">The state information.</param>
        private void BeginSalesmanTrip(Object stateInfo)
        {
            // Assume the StartButton_Click did all the error checking
            int populationSize = Convert.ToInt32(populationSizeTextBox.Text);
            int maxGenerations = Convert.ToInt32(maxGenerationTextBox.Text);
            int mutation = Convert.ToInt32(mutationTextBox.Text);
            int groupSize = NumericConstants.GROUP_SIZE;
            int seed = NumericConstants.SEED;
            int numberOfCloseCities = NumericConstants.NUMBER_OF_CLOSE_CITIES;
            int chanceUseCloseCity = NumericConstants.CHANCE_TO_USE_CLOSE_CITY;

            CityHelper.CalculateCityDistances(CityList, numberOfCloseCities);

            Salesman = new SalesmanBrains();
            Salesman.FoundNewBestRoute += salesman_foundNewBestTour;
            Salesman.Begin(populationSize, maxGenerations, groupSize, mutation, seed, chanceUseCloseCity, CityList);
            Salesman.FoundNewBestRoute -= salesman_foundNewBestTour;
            Salesman = null;
        }

        /// <summary>
        /// Handles the Click event of the clearCityListButton control.
        /// </summary>
        /// <param name="sender">The source of the event.</param>
        /// <param name="e">The <see cref="EventArgs"/> instance containing the event data.</param>
        private void clearCityListButton_Click(object sender, EventArgs e)
        {
            if (Salesman != null)
            {
                // ReSharper disable once LocalizableElement
                StatusLabel.Text = "Cannot alter city list while running";
                StatusLabel.ForeColor = Color.Red;
                return;
            }

            CityList.Clear();
            DrawCityList(CityList);
        }

        /// <summary>
        /// Handles the MouseDown event of the routeDiagram control.
        /// </summary>
        /// <param name="sender">The source of the event.</param>
        /// <param name="e">The <see cref="MouseEventArgs"/> instance containing the event data.</param>
        private void routeDiagram_MouseDown(object sender, MouseEventArgs e)
        {
            if (Salesman != null)
            {
                StatusLabel.Text = "Cannot alter city list while running";
                StatusLabel.ForeColor = Color.Red;
                return;
            }

            CityList.Add(new City(e.X, e.Y));
            DrawCityList(CityList);
        }

        /// <summary>
        /// Updates the city count.
        /// </summary>
        private void UpdateCityCount()
        {
            NumberCitiesValue.Text = CityList.Count.ToString();
        }

        #endregion
    }
}