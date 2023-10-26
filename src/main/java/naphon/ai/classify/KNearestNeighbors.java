package naphon.ai.classify;

import naphon.ai.ai.exceptions.AIException;

import java.util.*;

public class KNearestNeighbors {

    private Map<double[], Object> map = new HashMap<>();
    private List<Object> valuesGroup = new ArrayList<>();

    public void train(List<double[]> dataPoints, List<Object> values) throws AIException {

        if (dataPoints.size() != values.size() || !allArrayIsEqualLength(dataPoints)) {
            throw new AIException("Dataset is incomplete!");
        }

        if (dataPoints.get(0).length != 2) {
            throw new AIException("Datapoint is wrong please use (x,y)");
        }

        int size = dataPoints.size();

        for (int i = 0; i < size; i++) {
            map.put(dataPoints.get(i), values.get(i));
        }

        for (Object o : values) {
            if (!valuesGroup.contains(o)) {
                valuesGroup.add(o);
            }
        }
    }


    public List<Object> predict(double[] point, double k) throws AIException {
        List<double[]> nearestNeighbors = new ArrayList<>();
        for (double[] key : map.keySet()) {
            double distance = distance(point, key);
            if (distance < k) {
                nearestNeighbors.add(key);
            }
        }
        List<Object> nearestObject = new ArrayList<>();
        for (double[] near : nearestNeighbors) {
            nearestObject.add(map.get(near));
        }

        if (nearestNeighbors.size() == 0) {
            return null;
        }

        if (nearestNeighbors.size() == 1) {
            return Arrays.asList(nearestObject.get(0));
        }

        if (allEqual(nearestObject)) {
            return Arrays.asList(nearestObject.get(0));
        }

        if (allUnique(nearestObject)) {
            return nearestObject;
        }


        return getMost(nearestObject);
    }


    public List<Object> getMost(List<Object> objects) {
        Map<Object, Integer> countMap = new HashMap<>();

        for (Object item : objects) {
            countMap.put(item, countMap.getOrDefault(item, 0) + 1);
        }

        List<Object> equalCountElements = new ArrayList<>();
        for (Map.Entry<Object, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > 1) {
                equalCountElements.add(entry.getKey());
            }
        }


        return equalCountElements;
    }

    public boolean allEqual(List<Object> objects) {
        int e = 0;
        Object first = objects.get(0);
        for (Object o : objects) {
            if (!o.equals(first)) {
                e++;
            }
        }

        return e == 0;

    }

    public boolean allUnique(List<Object> objects) {
        boolean b = true;
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                if (objects.get(i).equals(objects.get(j))) {
                    b = false;
                    break;
                }
            }
        }

        return b;

    }

    public double distance(double[] point1, double[] point2) throws AIException {
        if (point1.length != 2 || point2.length != 2) {
            throw new AIException("DataPoint is wrong please use (x,y)");
        }

        double y = point2[1] - point1[1];
        double x = point2[0] - point1[0];
        return Math.sqrt(Math.pow(y, 2) + Math.pow(x, 2));

    }

    public boolean allArrayIsEqualLength(List<double[]> list) {
        int firstLength = list.get(0).length;

        for (double[] d : list) {
            if (d.length != firstLength) {
                return false;
            }
        }

        return true;


    }

}
