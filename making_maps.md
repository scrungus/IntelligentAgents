#### Making Maps


The structure of a map is as defined below;


`id`: The name of the scenario. Used to load scenario via the `mas2j` file.


`agent`: Agents definition object

    -  count: the maximum no. of agents in the scenario
    
    -  placement: location where the agents should be placed. When in use, each array element should be in the form of `{ x: <int>, y: <int>}`
    
`display`: Determines if the window launched in fullscreen or not. The only useful parameter is the `full_screen` parameter.

`energy`: Energy specification for the scenario.


    -  adjustments: no need to discuss this
    
    -  baseline: base energy cost for each action 
    
    -  band: energy category. Valid options are `extremely_high`,  `very_high`,  `high`,  `medium`, `low`, `very_low` and `extremely_low`. Set this variable 
        to a number should you need to set a specific energy value.
        
    - idle_cost: cost per reasoning cycle of idling and not acting.
    
`grid`: Defines the grid specifications


    -   dynamic: specifies if the map should be dynamic or based on specified dimension. Once this is set to `true`
    
    -   height:  an integer specifying the height of the map.
    
    -   width: an integer specifying the width of the map.
    
    -   wrap: ignore this flag.... not in use internally.
    
`obstacles`:  Obstacles definition object


    -   band: the amount of obstacles to place on the map. The allowed categories are: `none`, `very_sparse`, `sparse`, `medium` and  `high`. If a numeric value is passed into this, it denotes the amount of blocks that would be covered by an obstacle.
    
    -  placement: location where the obstacles should be placed. When in use, each array element should be in the form of `{ x: <int>, y: <int>}`
    
    
`resources`: Resource definition object


    -   count: No. of resources per deposit
    
    -   deposits: No. of resource deposits
    
    -   type: Array of types allowed. Resource types available. Options: `gold`, `diamond`.
    
    -   placement: location where the resources should be placed. When in use, each array element should be in the form of `{ x: <int>, y: <int>, type: <string>}`
