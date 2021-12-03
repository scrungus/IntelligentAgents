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
	!pickup;
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

-!dropoff0(Qty) : true 
	<- !dropoff0(Qty).  
    
+! deposit0(Qty) : true
  <- .print("depositing.")
  for ( .range(I,1,Qty) ) {
      		deposit("gold");
      	}
      .print("deposit success").
  
-! deposit0(Qty) : true
	<-.print("deposit failed");.

+!wait : true
	<- -obstructed(Xt,Yt,Xl,Yl)[source(percept)];
		.wait(1000);
      	.
      	
+obstructed(Xt,Yt,Xl,Yl) : collecting
	<- .print("collect failed");
		-collecting;
		mapping.log(Xt,Yt);
		!collect(Xl,Yl);
		.

+obstructed(Xt,Yt,Xl,Yl) : dropoff0
	<- .print("dropoff failed");
		-dropoff0(Qty);
		mapping.log(Xt,Yt);
		!wait;
		.
	
+obstructed(Xt,Yt,Xl,Yl) : dropoff1
	<- .print("dropoff failed");
		-dropoff1(Qty,Capacity); 
		mapping.log(Xt,Yt);
		!wait;
		.			

	