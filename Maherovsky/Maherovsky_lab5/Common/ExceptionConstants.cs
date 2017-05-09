namespace SalesmanGA.Common
{
    /// <summary>
    /// ExceptionConstants class
    /// </summary>
    public static class ExceptionConstants
    {
        public const string POPULATION_ERROR = "You must specify a Population Size.";

        public const string MAX_GENERATION_ERROR = "You must specify a Maximum Number of Generations.";

        public const string MUTATION_PERCENTAGE_ERROR = "Mutation must be between 0 and 100.";

        public const string MIN_CITY_COUNT_ERROR = "You must either load a City List file, or click the map to place at least 5 cities. ";

        public const string FILE_NOT_FOUND_ERROR = "File not found: ";

        public const string XML_ERROR = "Cities XML file is not valid";
    }
}
