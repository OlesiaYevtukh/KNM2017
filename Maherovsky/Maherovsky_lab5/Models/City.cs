using System;
using System.Collections.Generic;
using System.Drawing;

namespace SalesmanGA.Models
{
    /// <summary>
    /// An individual City in our tour.
    /// </summary>
    public class City
    {
        #region Constructors

        /// <summary>
        /// Constructor that provides the city location.
        /// </summary>
        /// <param name="x">X position of the city.</param>
        /// <param name="y">Y position of the city.</param>
        public City(int x, int y)
        {
            Location = new Point(x, y);
        }

        #endregion

        #region Public properties

        /// <summary>
        /// The location of this city.
        /// </summary>
        public Point Location { get; set; }

        /// <summary>
        /// The distance from this city to every other city.
        /// </summary>
        public List<double> Distances { get; set; } = new List<double>();

        /// <summary>
        /// A list of the cities that are closest to this one.
        /// </summary>
        public List<int> CloseCities { get; } = new List<int>();


        #endregion

        #region Public methods

        /// <summary>
        /// Find the cities that are closest to this one.
        /// </summary>
        /// <param name="numberOfCloseCities">When creating the initial population of tours, this is a greater chance
        /// that a nearby city will be chosen for a link. This is the number of nearby cities that will be considered close.</param>
        public void FindClosestCities(int numberOfCloseCities)
        {
            int shortestCity = 0;
            double[] dist = new double[Distances.Count];
            Distances.CopyTo(dist);

            if (numberOfCloseCities > Distances.Count - 1)
            {
                numberOfCloseCities = Distances.Count - 1;
            }

            CloseCities.Clear();

            for (int i = 0; i < numberOfCloseCities; i++)
            {
                var shortestDistance = Double.MaxValue;
                for (int cityNum = 0; cityNum < Distances.Count; cityNum++)
                {
                    if (dist[cityNum] < shortestDistance)
                    {
                        shortestDistance = dist[cityNum];
                        shortestCity = cityNum;
                    }
                }
                CloseCities.Add(shortestCity);
                dist[shortestCity] = Double.MaxValue;
            }
        }

        #endregion

    }
}
