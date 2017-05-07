using System;
using System.Windows.Forms;

namespace SalesmanGA
{
    /// <summary>
    /// Contains the Main that starts this form.
    /// </summary>
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new SalesmanGaForm());
        }
    }
}
