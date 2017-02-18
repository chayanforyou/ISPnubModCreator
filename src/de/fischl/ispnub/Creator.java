package de.fischl.ispnub;

import java.io.*;
import java.util.Arrays;

/**
 * Main application entry point of ISPnubCreator
 *
 * @author Thomas Fischl <tfischl@gmx.de>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
public class Creator {   

    public static final int SCRIPT_STARTADDRESS = 0x1000;
    public static final int UC_MEMORY_SIZE = 128 * 1024;
    public static final int TARGET_MEMORY_SIZE = 256 * 1024;
    public static String VERSION = "1.4";
    
    public static void main(String[] args) throws IOException {

        System.out.println();
        System.out.println("ISPnubCreator v" + VERSION);
        System.out.println("HEX file creator for ISPnub modules");
        System.out.println();
        
        // check if there is scriptfile & ISPnubAVR argument
        if (args.length <= 2) {
            System.out.println("Usage: java -jar ISPnubCreator.jar scriptfile ISPnubAVR [outfile (ispnub.hex)]");
            return;
        }
		
		String ISPnubAVR = (args[1]).toLowerCase();
		String lowFuse="";
		String highFuse="";
		String lockFuse="";
		switch(ISPnubAVR) {
			case "atmega1284p":
			case "atmega1284p_compat":
				lowFuse="0xE2";
				highFuse="0xD9";
				lockFuse="0x3C";
			case "atmega328_8mhz":
			case "atmega328_16mhz":
				
			break;
			default:
				System.out.println("AVR not supported");
				return;
		}
		
        
        // determine outfile name
        String outfilename = "ispnubInclTargetFW_" + ISPnubAVR + ".hex";
        if (args.length >= 3) {
            outfilename = args[2];
        }
        
        // prepare flash memory
        byte[] mem = new byte[UC_MEMORY_SIZE];
        Arrays.fill(mem, (byte)0xff);
        
        // read in main hex file and place it on start of flash memory
		System.out.print("Reading main hex file \"" + ISPnubAVR + ".hex\"...");
        HexFile.read(new InputStreamReader(Creator.class.getResourceAsStream("/res/ispnubmod_" + ISPnubAVR + ".hex") ), mem, 0);
		System.out.println(" done.");
		
        // parse script file
        System.out.print("Parse script file \"" + args[0] + "\"...");
        int lastpos = ISPScript.parse(args[0], mem, SCRIPT_STARTADDRESS);
        System.out.println(" done.");
        
        // write controller memory to out file
        System.out.print("Write output file \"" + outfilename + "\"...");
        HexFile.write(outfilename, mem, lastpos);
        System.out.println(" done.");
        
        // we have finished
        System.out.println();
        System.out.println("Finished! Now you can flash the ISPnub module, e.g. with avrdude:");
        System.out.println("avrdude -c YOURPROGRAMMER -p " + ISPnubAVR + "-U lfuse:w:" + lowFuse + ":m -U hfuse:w:" + highFuse + ":m -U flash:w:" + outfilename + " -U lock:w:" + lockFuse + ":m");
        System.out.println();
    }
}
