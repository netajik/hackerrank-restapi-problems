package javaprograms;

import java.util.*;

import junit.framework.*;

interface IPackage {
    int getId();

    double getWeight();

    double getHeight();

    double getWidth();

    double getLength();
}

interface IShipment {
    void insert(IPackage pkg);

    void remove(int id);

    Map<String, Double> getTotalCosts();

    List<IPackage> getPackages();
}

class Package implements IPackage {
    private int id;
    private double weight;
    private double height;
    private double width;
    private double length;

    public Package(int id, double weight, double height, double width, double length) {
        this.id = id;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }
}

class Cargo implements IShipment {
    private List<IPackage> packages = new ArrayList<>();
    private double distance; // Added distance variable

    public Cargo(double distance) {
        this.distance = distance;
    }

    public void insert(IPackage pkg) {
        packages.add(pkg);
    }

    public void remove(int id) {
        packages.removeIf(pkg -> pkg.getId() == id);
    }

    public Map<String, Double> getTotalCosts() {
        Map<String, Double> totalCosts = new HashMap<>();
        double totalTransportCost = 0;
        double totalServiceCost = 0;
        for (IPackage pkg : packages) {
            double volume = pkg.getHeight() * pkg.getWidth() * pkg.getLength();
            double transportCost = 0;
            double serviceCost = 0;

            if (pkg instanceof Package) {
                Package p = (Package) pkg;
                if (p.getWeight() <= 10) {
                    transportCost = 0.5 * volume;
                    serviceCost = 0.5 * p.getWeight() + 0.5 * distance;
                } else if (p.getWeight() <= 20) {
                    transportCost = 0.75 * volume;
                    serviceCost = 0.75 * p.getWeight() + 0.75 * distance;
                } else {
                    transportCost = 0.625 * volume;
                    serviceCost = 0.625 * p.getWeight() + 0.625 * distance;
                }
            }
            
            totalTransportCost += transportCost;
            totalServiceCost += serviceCost;
        }

        totalCosts.put("Transport", totalTransportCost);
        totalCosts.put("Service", totalServiceCost);
        return totalCosts;
    }

    public List<IPackage> getPackages() {
        return packages;
    }
}

public class PackageCosts {

    public static void main(String[] args) {
        Cargo cargo = new Cargo(100); // Assuming distance is 100 for this test
        cargo.insert(new Package(1, 10, 2, 3, 4)); // Standard package
        cargo.insert(new Package(2, 20, 3, 4, 5)); // Hazardous package
        cargo.insert(new Package(3, 30, 4, 5, 6)); // Fragile package

        // Standard package: transport cost = 0.5 * volume = 24, service cost = 0.5 * weight + 0.5 * distance = 12.0
        // Hazardous package: transport cost = 0.75 * volume = 60, service cost = 0.75 * weight + 0.75 * distance = 45.0
        // Fragile package: transport cost = 0.625 * volume = 120, service cost = 0.625 * weight + 0.625 * distance = 75.0
        // Overall totals: transport = 204, service = 275, overall = 479

        Assert.assertEquals(132.0, cargo.getTotalCosts().get("Transport"));
        Assert.assertEquals(226.25, cargo.getTotalCosts().get("Service"));
      
    }
}
