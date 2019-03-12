package oleksandr.dolhanenko.networkjava.utils;

import oleksandr.dolhanenko.networkjava.model.Computer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ComputerGenerator {
    private static int[] ramSizes = {2, 4, 8, 16, 32};
    private static String[] cpuNames = {
            "Xeon 64 bit Core",
            "Intel Core 2",
            "Intel Pentium Dual-Core",
            "Intel Pentium",
            "Intel Core i3 1st Gen",
            "Intel Core i3 2nd Gen",
            "Intel Core i3 3rd Gen",
            "Intel Core i5 1st Gen",
            "Intel Core i5 2nd Gen",
            "Intel Core i5 3rd Gen",
            "Intel Core i7 1st Gen",
            "Intel Core i7 2nd Gen",
            "Intel Core i7 3rd Gen",
            "Intel Core i7 4th Gen",
            "Intel Core i7 5th Gen",
            "Intel Tera-Scale",
    };
    private static String[] gpuNames = {
            "Nvidia Pre-GeForce",
            "Nvidia GeForce2 series",
            "Nvidia GeForce2 GO series",
            "Nvidia GeForce3 series",
            "Nvidia GeForce4 series",
            "Nvidia GeForceFX series",
            "Nvidia GeForce 300M series",
            "Nvidia GeForce 400M series",
            "Nvidia GeForce 500M series",
            "Nvidia GeForce 600M series",
            "Nvidia GeForce 700M series",
            "Nvidia GeForce 800M series",
    };
    private static final Random random = new Random();

    public static List<Computer> generate(int count) {
        List<Computer> computers = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < count; i++) {
            String randomGpu = gpuNames[random.nextInt(gpuNames.length)];
            String randomCpu = cpuNames[random.nextInt(cpuNames.length)];
            int randomRamSize = ramSizes[random.nextInt(ramSizes.length)];
            computers.add(new Computer(i, randomCpu, randomGpu, randomRamSize));
        }
        return computers;
    }
}
