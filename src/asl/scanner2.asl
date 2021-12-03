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
   <- rover.ia.get_map_size(Width, Height); 
   	 rover.ia.check_config(Capacity,Scanrange,Resourcetype);
   	 +range(Scanrange);
   	for ( .range(I,1,(Width-1)/2,Scanrange) ) {
   	  .print("I : ",I);
   	   ?range(R);
 	   move(R,0);
 	   mapping.log(R,0);
 	   for ( .range(J,1,Height-1-Scanrange,Scanrange) ) {
   	  	.print("J : ",J);
   	  	?range(R);
   	  	scan(R);
   	  	move(0,R);
   	  	mapping.log(0,R);
   	  }
   	 }
   	.send(collector,tell,done);
   	mapping.get_base(X,Y);
   	move(X,Y);
   	mapping.log(X,Y);
	.

-! explore: true
	<- .print("explore failed");
		!explore.
   
@resource_found[atomic]
+ resource_found(RsType, Qty, XDist, YDist) : true
	<-	.print("RESOURCE FOUND");
	mapping.get_loc(X,Y);
	.print("Agent Location : (",X,",",Y,")");
	mapping.new_resource(X+XDist,Y+YDist,RsType,Qty);
    .

+!wait : true
	<- -obstructed(Xt,Yt,Xl,Yl)[source(percept)];
		.wait(1000);
      	.
      	
+obstructed(Xt,Yt,Xl,Yl) : exploring
	<- .print("explore failed");
		-exploring;
		mapping.log(Xt,Yt);
		!wait.

	