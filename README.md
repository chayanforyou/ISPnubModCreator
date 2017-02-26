ISPnubModCreator
=============

Creates hex file for ISPnubMod (https://github.com/mgroel/ISPnubMod, http://www.fischl.de/ispnub/) from given programming instruction script.
ISPnubModCreator is written in Java.

Usage
-----

Example of programming instruction script, programming an ATmega8 ("../test/main.hex" contains the firmware
for the target Atmega8). "test.ispnub":
```
CONNECT 2000000                           ; connect with SCK = 2 MHz 
SPIVERIFY 0x30, 0x00, 0x00, 0x00, 0x1E    ; check signature byte 0x00 (0x1E = manufactured by Atmel)
SPIVERIFY 0x30, 0x00, 0x01, 0x00, 0x93    ; check signature byte 0x01 (0x93 = 8KB Flash memory)
SPIVERIFY 0x30, 0x00, 0x02, 0x00, 0x07    ; check signature byte 0x02 (0x07 = ATmega8 device)
SPIWRITE  0xAC, 0x80, 0x00, 0x00          ; chip erase
WAIT 2                                    ; wait 20 ms
FLASH ../test/main.hex, 0, 64             ; flash given hex file starting at flash address 0 with pagesize 64
DISCONNECT                                ; disconnect SPI
DECCOUNTER 10                             ; allow 10 programming cycles
END
```

Call ISPnubModCreator to create the hex file for ISPnubMod. Compared to the original ISPnubCreator there are several AVR supported on the ISPnub. The calling syntax for ISPnubCreator is as following:
```
java -jar ISPnubCreator.jar test.ispnub [atmega1284|atmega1284_compat|atmega328_16MHz|atmega16|atmega32|atmega644] ispnub.hex
```
Now the generated "ispnub.hex" can be flashed with your favorite programmer into the ISPnub module.

Supported Boards and AVR
-------------
The firmware is optimized for use on the ISPnubMod-Board (see https://github.com/mgroel/ISPnubMod). Nevertheless there is a compatibility-firmware included to be used on the original ISPnub-Board. To use this firmware call ISPnubModCreator with the argument "atmega1284_compat".

ATmega1284 (tested)
ATmega328@16MHz (tested)
ATmega16
ATmega32
ATmega644



Build
-----
Ant is used to build the application from Java source code. To create the JAR file, use
```
ant jar
```

License
-------

ISPnubCreator, creator for ISPnub hex files

Copyright (C) 2013  Thomas Fischl (http://www.fischl.de)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
