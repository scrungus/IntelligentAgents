// Agent scen2_bot in project ia_submission
/* Initial beliefs and rules */	
/* Initial goals */

!init.

+!init : true
 <- +init;
 	mapping.agent_init;
 	-init;
 	rover.ia.get_map_size(Width, Height);
 	mapping.map_init(Width, Height);
 	!explore
 	.
 	
-!init : init
	<- -init;
	!init.

/* Plans */
+! explore : true
   <- mapping.get_nearest(XDist,YDist);
      .print("explore: nearest : ",XDist," ",YDist);
   	  mapping.get_base(XDist1,YDist1);
   	  .print("explore: base : ",XDist1," ",YDist1);
      if(XDist \== XDist1 & YDist \== YDist1){
      	.print("going to resource ",XDist," ",YDist);
      	+exploring;
      	move(XDist,YDist);
      	mapping.log(XDist,YDist);
      	-exploring;
      	+pickup;
      	!pickup;
      	-pickup;
      	!explore;
      }
   	  else{
   	  	.print("randomly exploring");
   	  	+exploring
   	  	move(2,1);
   	  	mapping.log(2,1);
   	  	-exploring
      	scan(3);
      	!explore;
   	  }.

-! explore: true
	<- .print("explore failed");
		!explore.
	 
+! pickup : true
	<- rover.ia.check_config(Capacity,Scanrange,Resourcetype);
	mapping.check_resource(Qty);
	.print("Qty returned :",Qty);
	if (Qty <= Capacity){
		.print("collecting");
      	for ( .range(I,1,Qty) ) {		
      		collect("gold");
      	}
      	.print("collected");
      	mapping.update_resource(0);
      	!dropoff0(Qty);
      }
      elif(Qty > Capacity){
      	.print("collecting");
      	for ( .range(I,1,Capacity) ) {
      		collect("gold");
      	}
      	.print("collected");
      	mapping.update_resource(Qty-Capacity);
      	!dropoff1(Qty,Capacity);
      }
      else{
      	.fail;
      }.

+!dropoff0(Qty) : true 
	<- mapping.get_base(XD, YD);
      	.print("going to base : ",XD, " ",YD);
      	+dropoff0;
      	move(XD,YD);
      	mapping.log(XD,YD);
      	-busy;
      	-dropoff0;
      	!deposit0(Qty);
      	.print("finished");
	.

-!dropoff0(Qty) : true 
	<- !dropoff0(Qty).
	
+!dropoff1(Qty,Capacity) : true 
	<- mapping.get_base(XD, YD);
      	.print("going to base : ",XD, " ",YD);
      	+dropoff1;
      	move(XD,YD);
      	mapping.log(XD,YD);
      	-dropoff1;
      	!deposit0(Capacity);
      	.print("finished");.
 
-!dropoff1(Qty,Capacity) : true 
	<- !dropoff1(Qty,Capacity).
    
+ resource_found(RsType, Qty, XDist, YDist) : true
	<-	.print("RESOURCE FOUND");
	mapping.new_resource(XDist,YDist,"g",Qty);
    .
    
+! deposit0(Qty) : true
  <- .print("depositing.")
  for ( .range(I,1,Qty) ) {
      		deposit("gold");
      	}
      .print("deposit success").
  
-! deposit0(Qty) : true
	<-.print("deposit failed");.

/* 	  
+!recover(Xt,Yt,Xl,Yl,Qty): true
	<- .print("recovering");
	-obstructed(Xt,Yt,Xl,Yl)[source(percept)];
	+move_resource(Qty);
	move(Xl,Yl);
	mapping.log(Xl,Yl);
	rover.ia.check_config(Capacity,Scanrange,Resourcetype);
	mapping.new_resource("g",Qty);
	mapping.get_base(X,Y);
	.print("new resource ",-X," ",-Y);
	+busy;
	-move_resource(Qty);
	!pickup;
	.print("pickupdropoff finished")
	.print("exploring")
    !explore;.
    
+ obstructed(Xt,Yt,Xl,Yl) : move_resource(Qty)
	<- .print("obstructed r");
	-move_resource(Qty);
	mapping.log(Xt,Yt);
	!recover(Xt,Yt,Xl,Yl,Qty); 
	.
*/

+!wait : true
	<- -obstructed(Xt,Yt,Xl,Yl)[source(percept)];
		.wait(1000);
      	.
      	
+obstructed(Xt,Yt,Xl,Yl) : exploring
	<- .print("explore failed");
		-exploring;
		mapping.log(Yt,Yt);
		!wait.

+obstructed(Xt,Yt,Xl,Yl) : dropoff0
	<- .print("dropoff failed");
		-dropoff0(Qty);
		mapping.log(Yt,Yt);
		!wait;
		.
	
+obstructed(Xt,Yt,Xl,Yl) : dropoff1
	<- .print("dropoff failed");
		-dropoff1(Qty,Capacity); 
		mapping.log(Yt,Yt);
		!wait;
		.			

	