using System;
using System.Collections.Generic;
using SalesmanGA.Models;

namespace SalesmanGA.Helpers
{
    /// <summary>
    /// CityHelper class
    /// </summary>
    public static class CityHelper
    {
        /// <summary>
        /// Calculates the city distances.
        /// </summary>
        /// <param name="cityList">The city list.</param>
        /// <param name="numberOfCloseCities">The number of close cities.</param>
        public static void CalculateCityDistances(IList<City> cityList, int numberOfCloseCities)
        {
            foreach (City city in cityList)
            {
                city.Distances.Clear();

                for (int i = 0; i < cityList.Count; i++)
                {
                    city.Distances.Add(Math.Sqrt(Math.Pow(city.Location.X - cityList[i].Location.X, 2D) +
                                                 Math.Pow(city.Location.Y - cityList[i].Location.Y, 2D)));
                }
            }

            foreach (City city in cityList)
            {
                city.FindClosestCities(numberOfCloseCities);
            }
        }
    }
}
