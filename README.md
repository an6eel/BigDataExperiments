# Big Data Spark Experiments

## How to run the experiments

1. Clean package

    ```shell script
    ./clean.sh
    ```

2. Compile package

    ```shell script
    ./compile.sh
    ````

3. Move `target/BDII-experiment-VERSION-jar-with-dependencies.jar` to current folder `.`

4. Run experiments

    `VERSION` is the experiments version. You can especify it in the `pom.xml`
    
    - Run DecisionTree experiments 
    
        ````shell script
         ./runDTExperiments.sh VERSION
        ````
       
    -  Run RandomForest experiments
    
        ````shell script
         ./runRFExperiments.sh VERSION
        ````
       
    - Run GradientBoosted experiments
    
        ````shell script
          ./runGBTExperiments.sh VERSION
        ````
      
    - Run one experiment
    
        ```shell script
          ./runExperiment EXPERIMENT VERSION
        ```
      
  There are 15 experiments
    
   - *1-5* are DT experiments
   - *6-10* are RF experiments
   - *11-15* are GBT experiments