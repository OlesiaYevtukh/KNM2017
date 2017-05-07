using System;
using System.Collections.Generic;
using SalesmanGA.Models;

namespace SalesmanGA.Event_Args
{
    /// <summary>
    /// Event arguments when the TSP class wants the GUI to draw a tour.
    /// </summary>
    public class SalesmanEventArgs : EventArgs
    {
        #region Constructors

        /// <summary>
        /// Default Constructor.
        /// </summary>
        public SalesmanEventArgs()
        {
        }

        /// <summary>
        /// Constructor that sets all the properties.
        /// </summary>
        /// <param name="cityList">The list of cities to draw.</param>
        /// <param name="bestTour">The tour that connects all the cities.</param>
        /// <param name="generation">Which generation is this.</param>
        /// <param name="complete">Is this the last update before we are done.</param>
        public SalesmanEventArgs(List<City> cityList, Route bestTour, int generation, bool complete)
        {
            CityList = cityList;
            BestTour = bestTour;
            Generation = generation;
            Complete = complete;
        }

        #endregion

        #region Public properties

        /// <summary>Public property for list of cities.</summary>
        public List<City> CityList { get; set; }

        /// <summary>Public property for the tour of the cities.</summary>
        public Route BestTour { get; set; }

        /// <summary>
        /// Gets or sets the generation.
        /// </summary>
        public int Generation { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether this <see cref="SalesmanEventArgs"/> is complete.
        /// </summary>
        public bool Complete { get; set; }

        #endregion
    }
}