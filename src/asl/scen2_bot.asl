// Agent scen2_bot in project ia_submission
/* Initial beliefs and rules */
path_to_resource(0,0,0).
obstructed(0,0,0,0).
busy(0).
/* Initial goals */

!init.

+!init : true
 <- mapping.agent_init;
 	!explore
 	.

/* Plans */
+! explore : true
   <- ?path_to_resource(XDist, YDist,Qty);
      if(XDist \== 0 & YDist \== 0 & Qty \== 0){
      	.print("going to resource");
      	move(XDist,YDist);
      	mapping.log(XDist,YDist);
      	rover.ia.log_movement(XDist,YDist);
      	scan(1);
      }
   	  else{
   	  	.print("randomly exploring");
   	  	move(2,1);
   	  	mapping.log(2,1);
      	rover.ia.log_movement(2,1);
      	scan(3);
   	  }.
   	  
-! explore: true
	<- .print("xplore failed");
	!explore
      .
      
+! pickupdropoff(Qty) : true
	<- rover.ia.check_config(Capacity,Scanrange,Resourcetype);
	if (Qty <= Capacity){
		.print("collecting");
      	for ( .range(I,1,Qty) ) {
      		
      		collect("gold");
      	}
      	.print("collected");
      	rover.ia.get_distance_from_base(XD, YD);
      	.print("Distance from base: ",XD, " ",YD);
      	+pickup(Qty);
      	move(XD,YD);
      	rover.ia.log_movement(XD,YD);
      	+path_to_resource(0,0,0);
      	-busy;
      	-pickup(Qty);
      	!deposit0(Qty);
      }
      else{
      	.print("collecting");
      	for ( .range(I,1,Capacity) ) {
      		collect("gold");
      	}
      	.print("collected");
      	rover.ia.get_distance_from_base(XD, YD);
      	.print("Distance from base: ",XD, " ",YD);
      	+pickup(Capacity);
      	move(XD,YD);
      	-pickup(Capacity);
      	rover.ia.log_movement(XD,YD);
      	?path_to_resource(X,Y,Qty);
      	+path_to_resource(X,Y,Qty-Capacity);
      	!deposit0(Capacity);
      }.

-! pickupdropoff(Qty) : true
	<- .print("pickupdropoff failed");
	!pickupdropoff(Qty);
	.

+ resource_not_found : true
   <- !explore.
    
+ resource_found(RsType, Qty, XDist, YDist) : true
	<-	.print("resource found");
	+move_resource
	move(XDist, YDist);
	rover.ia.log_movement(XDist, YDist);
	rover.ia.check_config(Capacity,Scanrange,Resourcetype);
	rover.ia.get_distance_from_base(XD, YD);
	.broadcast(tell,resource_location(XD,YD,Qty-Capacity));
	.print("broadcast : ",resource_location(XD,YD,Qty-Capacity));
	+busy;
	-move_resource;
	!pickupdropoff(Qty);
    !explore;
    .
    
+! deposit0(Qty) : true
  <- .print("depositing.")
  for ( .range(I,1,Qty) ) {
      		deposit("gold");
      	}
    !explore.
  
-! deposit0(Qty) : true
	<-.print("deposit failed");
	  .
+!recover(Xt,Yt,Xl,Yl): true
	<- move(Xl,Yl);
	.print("recovering");
	rover.ia.log_movement(Xl,Yl);
	.print("move successful");
	rover.ia.check_config(Capacity,Scanrange,Resourcetype);
	rover.ia.get_distance_from_base(XD, YD);
	+busy;
	-move_resource;
	!pickupdropoff(Qty);
    !explore;.
    
+ obstructed(Xt,Yt,Xl,Yl) : move_resource
	<- .print("obstructed r"); 
	-obstructed(Xt,Yt,Xl,Yl);
	rover.ia.log_movement(Xt,Yt);
	!recover(Xt,Yt,Xl,Yl);
	.

+ obstructed(Xt,Yt,Xl,Yl) : pickup(Qty)
 	<-.print("obstructed p"); 
	rover.ia.log_movement(Xt,Yt);
	move(Xl,Yl);
	rover.ia.log_movement(Xl,Yl);
	!pickupdropoff(Qty)
 	.
 	
+resource_location(XDist,YDist,Qty) : true
 <- if(not busy){
 		.print("updating path to resource");
 		rover.ia.get_distance_from_base(XD, YD);
 		+path_to_resource(XD-XDist,YD-YDist,Qty);
 		.print("new path: ",path_to_resource(XD-XDist,YD-YDist,Qty));
 	}
 	.

	