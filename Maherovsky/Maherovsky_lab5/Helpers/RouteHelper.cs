using System;
using System.Collections.Generic;
using SalesmanGA.Models;

namespace SalesmanGA.Helpers
{
    /// <summary>
    /// RouteHelper class
    /// </summary>
    public static class RouteHelper
    {
        #region Public properties

        /// <summary>
        /// Gets or sets the best route.
        /// </summary>
        public static Route BestRoute { get; set; }

        #endregion

        #region Public methods

        /// <summary>
        /// Create the initial set of random tours.
        /// </summary>
        /// <param name="populationSize">Number of tours to create.</param>
        /// <param name="cityList">The list of cities in this tour.</param>
        /// <param name="rand">Random number generator. We pass around the same random number generator, so that results between runs are consistent.</param>
        /// <param name="chanceToUseCloseCity">The odds (out of 100) that a city that is known to be close will be used in any given link.</param>
        public static List<Route> CreateRandomPopulation(int populationSize, List<City> cityList, Random rand, int chanceToUseCloseCity)
        {
            List<Route> population = new List<Route>();
            for (int tourCount = 0; tourCount < populationSize; tourCount++)
            {
                Route route = new Route(cityList.Count);

                // Create a starting point for this tour
                var firstCity = rand.Next(cityList.Count);
                var lastCity = firstCity;

                for (int city = 0; city < cityList.Count - 1; city++)
                {
                    int nextCity;
                    do
                    {
                        // Keep picking random cities for the next city, until we find one we haven't been to.
                        if ((rand.Next(100) < chanceToUseCloseCity) && (cityList[city].CloseCities.Count > 0))
                        {
                            // 75% chance will will pick a city that is close to this one
                            nextCity = cityList[city].CloseCities[rand.Next(cityList[city].CloseCities.Count)];
                        }
                        else
                        {
                            // Otherwise, pick a completely random city.
                            nextCity = rand.Next(cityList.Count);
                        }
                        // Make sure we haven't been here, and make sure it isn't where we are at now.
                    } while ((route[nextCity].Connection2 != -1) || (nextCity == lastCity));

                    // When going from city A to B, [1] on A = B and [1] on city B = A
                    route[lastCity].Connection2 = nextCity;
                    route[nextCity].Connection1 = lastCity;
                    lastCity = nextCity;
                }

                // Connect the last 2 cities.
                route[lastCity].Connection2 = firstCity;
                route[firstCity].Connection1 = lastCity;

                route.DetermineFitness(cityList);

                population.Add(route);

                if ((BestRoute == null) || (route.Fitness < BestRoute.Fitness))
                {
                    BestRoute = route;
                }
            }
            return population;
        }

        #endregion
    }
}
