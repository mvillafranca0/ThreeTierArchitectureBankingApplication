/**************************************************************************************
*	Program Author: Michael Villafranca for CSCI 4380 Systems Development with Java	  *
*	Date: April, 2021													              *
***************************************************************************************/

import java.lang.*; //including Java packages used by this program

public class SignUpControlV2
{
    private SignUpBOV2 SU_BO;

    public SignUpControlV2(String arg1, String arg2)
    {
		SU_BO = new SignUpBOV2(arg1, arg2);
	}
}