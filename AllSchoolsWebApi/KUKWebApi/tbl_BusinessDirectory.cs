//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace KUKWebApi
{
    using System;
    using System.Collections.Generic;
    
    public partial class tbl_BusinessDirectory
    {
        public int col_BusinessID { get; set; }
        public string col_BusinessName { get; set; }
        public string col_BusinessCategory { get; set; }
        public string col_BusinessAddress { get; set; }
        public string col_BusinessDescription { get; set; }
        public string col_PostedBy { get; set; }
        public string col_BusinessContact { get; set; }
        public string col_BusinessEmail { get; set; }
    
        public virtual AspNetUser AspNetUser { get; set; }
    }
}
