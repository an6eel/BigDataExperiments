# Experiments

## First Round Experiments

### Decision Tree

*Parameters by default*

- Impurity = gini
- maxDepth = 5
- maxBins = 32

### Random Forest

*Parameters by default*

- numTrees = 100
- Impurity = gini
- featureSubsetStrategy = auto
- maxDepth = 5
- maxBins = 32

### GradientBoosted Trees

*Parameters by default*

- numIterations = 100
- maxDepth = 5

### ROS OverRate = 0.75

## Second Round Experiments

### Decision Tree

*Parameters*

- Impurity = gini
- maxDepth = 20
- maxBins = 64

### Random Forest

*Parameters*

- numTrees = 300
- Impurity = gini
- featureSubsetStrategy = auto
- maxDepth = 20
- maxBins = 64

### GradientBoosted Trees

*Parameters*

- numIterations = 250
- maxDepth = 10

### ROS OverRate = 1.0

## List Experiments 1st and 2nd round

1. Decision Tree + ROS
2. Decision Tree + RUS
3. Decision Tree + HME_BD
4. Decision Tree + ROS + HME_BD
5. Decision Tree + RUS + HME_BD
6. Random Forest + ROS
7. Random Forest + RUS
8. Random Forest + HME_BD
9. Random Forest + ROS + HME_BD
10. Random Forest + RUS + HME_BD
11. GradientBoosted + ROS
12. GradientBoosted + RUS
13. GradientBoosted + HME_BD
14. GradientBoosted + ROS + HME_BD
15. GradientBoosted + RUS + HME_BD
