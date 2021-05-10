/**
 ******************************************************************************
 * @file    LED Display Control Example/Model/LedModel.cs
 * @author  Adrian Wojcik  adrian.wojcik@put.poznan.pl
 * @version V2.0
 * @date    10-May-2021
 * @brief   LED display controller: LED data model - nullable RGB color format
 ******************************************************************************
 */

namespace LedDisplayExample.Model
{
    public class LedModel
    {
        public byte? R; //!< Red color component 
        public byte? G; //!< Green color component 
        public byte? B; //!< Blue color component 

        /**
         * @brief Default constructor
         */
        public LedModel()
        {
            R = null;
            G = null;
            B = null;
        }

        /**
         * @brief Check if all color components are null
         * @return False if all color component are null, True otherwise
         */
        public bool ColorNotNull()
        {
            return (R != null) & (G != null) & (B != null);
        }

        /**
         * @brief Sets all color components to null
         */
        public void Clear()
        {
            R = null;
            G = null;
            B = null;
        }
    }
}
