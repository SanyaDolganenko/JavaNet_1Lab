package oleksandr.dolhanenko.networkjava.model;

public class Computer {
    private int id;
    private String cpu;
    private String gpu;
    private int ramSize;


    public Computer(int id, String cpu, String gpu, int ramSize) {
        this.id = id;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ramSize = ramSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public int getRamSize() {
        return ramSize;
    }

    public void setRamSize(int ramSize) {
        this.ramSize = ramSize;
    }
}
