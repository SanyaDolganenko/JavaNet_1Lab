package oleksandr.dolhanenko.networkjava.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "computers")
public class Computer {
    @Id
    @Column(name = "computer_id")
    @GeneratedValue
    private int id;
    private String cpu;
    private String gpu;
    private int ramSize;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "user_computers",
            joinColumns = {@JoinColumn(name = "computer_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Computer() {
    }

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
