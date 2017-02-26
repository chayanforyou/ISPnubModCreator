@echo off

java -jar ISPnubCreator.jar ./compile_script_tiny13a.ispnub atmega1284 ./output/ispnub_atmega1284.hex
java -jar ISPnubCreator.jar ./compile_script_tiny13a.ispnub atmega1284_compat ./output/ispnub_atmega1284_compat.hex
java -jar ISPnubCreator.jar ./compile_script_tiny13a.ispnub atmega328_16MHz ./output/ispnub_atmega328_16MHz.hex
java -jar ISPnubCreator.jar ./compile_script_tiny13a.ispnub atmega16 ./output/ispnub_atmega16.hex
java -jar ISPnubCreator.jar ./compile_script_tiny13a.ispnub atmega32 ./output/ispnub_atmega32.hex
java -jar ISPnubCreator.jar ./compile_script_tiny13a.ispnub atmega644 ./output/ispnub_atmega644.hex


pause
