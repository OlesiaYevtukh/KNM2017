namespace SalesmanGA.Models
{
    /// <summary>
    /// An individual link between 2 cities in a tour.
    /// This city connects to 2 other cities.
    /// </summary>
    public class Link
    {
        /// <summary>
        /// Connection to the first city.
        /// </summary>
        public int Connection1 { get; set; }

        /// <summary>
        /// Connection to the second city.
        /// </summary>
        public int Connection2 { get; set; }
    }
}
