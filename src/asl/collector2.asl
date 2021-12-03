//collector
/* Initial beliefs and rules */	
/* Initial goals */
!init.

+!init : true
	<-+init;
 	mapping.agent_init;
 	-init;.

-!init : init
	<- -init;
	!init.
	
/* Plans */

+! collect(X,Y,RsType) : true
	<- rover.ia.check_config(Capacity,Scanrange,Resourcetype);
	if(RsType \== Resourcetype){
		.fail;
	}
	+collecting(RsType);
	move(X,Y);
	mapping.log(X,Y);
	-collecting(RsType);
	!pickup;
	.
	 
+! pickup : true
	<- rover.ia.check_config(Capacity,Scanrange,Resourcetype);
	collect(ResourceType);
	.send()
	.

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
      	
+done [source(scanner)]: true
	<-!explore.
      	
+obstructed(Xt,Yt,Xl,Yl) : collecting(RsType)
	<- .print("collect failed");
		-collecting(RsType);
		mapping.log(Xt,Yt);
		!collect(Xl,Yl,RsType);
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

	