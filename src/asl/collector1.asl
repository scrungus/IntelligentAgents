//collector

/* Initial beliefs and rules */	
/* Initial goals */
carrying(0).
!init.

+!init : true
	<-+init;
 	mapping.agent_init;
 	-init;.

-!init : init
	<- -init;
	!init.
	
/* Plans */

+! collect(X,Y) : true
	<- +collecting;
	.print("collecting...")
	mapping.get_path(X,Y,PX,PY);
	.print("going via (",PX,",",PY,")");
	move(PX,PY);
	mapping.log(PX,PY);
	-collecting;
	.print("finished moving to resource");
	!pickup;
	.
@collect(X,Y)[priority(1)]
-! collect(X,Y) : true
	<- .print("collect failed, trying again");
		!collect(X,Y);
		.
	 
+! pickup : true
	<- rover.ia.check_config(Capacity,Scanrange,Resourcetype);
	.print("picking up");
	collect("gold");
	?carrying(Q);
	-+carrying(Q+1);
	if(Q+1 == Capacity){
		.send(scanner1,tell,returning_to_base);
		!dropoff0(Capacity);
	}
	else{
	 .send(scanner1,tell,collected);	
	}
	.

@dropoff0(Qty)[atomic]
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

@dropoff0(Qty)[priority(1)]
-!dropoff0(Qty) : true 
	<- !dropoff0(Qty).  
    
+! deposit0(Qty) : true
  <- .print("depositing.")
  for ( .range(I,1,Qty) ) {
      		deposit("gold");
      	}
      -+carrying(0);
      .print("deposit success").
  
-! deposit0(Qty) : true
	<-.print("deposit failed");.

@avoid(Xt,Yt,Xl,Yl)[atomic]
+!avoid(Xt,Yt,Xl,Yl) : true
	<- -obstructed(Xt,Yt,Xl,Yl)[source(percept)];
		mapping.resolve(Xt,Yt,Xl,Yl,X,Y);
		for( .member(NX,X) ){
			.member(NY,Y)
			move(NX,NY);
			mapping.log(NX,NY);
		}
      	.

@obstructed(Xt,Yt,Xl,Yl)[priority(5)]
+obstructed(Xt,Yt,Xl,Yl): true
	<- .print("obstructed");
		if(exploring){
			-exploring;
			+obs;
			mapping.add_explore_point(Xt+Xl,Yt+Yl);
		}
		mapping.log(Xt,Yt);
		!avoid(Xt,Yt,Xl,Yl);
		.

	