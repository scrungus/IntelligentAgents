// Agent scen2_bot in project ia_submission
/* Initial beliefs and rules */	
/* Initial goals */
waitingrs(0).
moving(0).
!init.

+!init : true
 <- +init;
 	mapping.agent_init;
 	-init;
 	rover.ia.check_config(Capacity,Scanrange,Resourcetype);
 	rover.ia.get_map_size(Width, Height);
 	mapping.map_init(Width, Height,Scanrange);
 	!explore
 	.
 	
-!init : init
	<- -init;
	!init.

/* Plans */
+! explore : true
   <- .print("exploring");
   mapping.get_nearest('r',XN,YN,'G');
   rover.ia.get_map_size(Width, Height);
   if((XN \== Width & YN \== Height)){
		-exploring;
		.print("waiting at resource, taking path (",XN,",",YN,")");
		!wait_at_resource(XN,YN);	
		.fail;
   } 
   mapping.get_next_location(X,Y);
   +exploring
   .print("moving to (",X,",",Y,")")
   rover.ia.check_config(Capacity,Scanrange,Resourcetype);
   +range(Scanrange);
   -+moving(1);
   	move(X,Y);
   	-+moving(0);
    mapping.log(X,Y);
    ?range(R);
    scan(R);
   !explore.

-! explore: true
	<- .print("waiting").

@explore[priority(1)]
-! explore: obs
	<- .print("obstructed during explore, resuming");
	-obs; 
	!explore.
		
+! wait_at_resource (XDist, YDist): true
	<-.print("executing wait_at_resource");
	-+moving(1);
	move(XDist-1,YDist);
	-+moving(0);
	mapping.log(XDist-1,YDist);
	rover.ia.check_config(Capacity,Scanrange,Resourcetype);
	mapping.get_nearest('a',X,Y,'G');
	.print("telling collector to collect at (",X,",",Y,")");
	.send(collector1,achieve,collect(X,Y));
	.

@wait_at_resource(XDist, YDist)[priority(1)]
-! wait_at_resource(XDist, YDist): true
	<-	.print("obstructed during wait_at_resource, resuming");
		mapping.get_nearest('r',XN,YN,'G');
		!wait_at_resource(XN,YN);.
		
+ collected[source(collector1)]: true
	<- .print("received signal from collector"); 
		scan(1);
		if(scan_success){
			-scan_success;
		}
		else{
			-collected[source(collector1)];
			!handle_collector_msg;
		}
		
		.
		
+returning_to_base[source(collector1)] : true
	<- .print("collector is returning to base");
		scan(1);
		if(scan_success){
			-scan_success;
		}
		else{
			-returning_to_base[source(collector1)];
			!handle_collector_msg;
		}.
	
+!handle_collector_msg : true
	<- .print("resource finished");
		rover.ia.check_config(Capacity,Scanrange,Resourcetype);
		mapping.get_nearest('a',X,Y,'G');
		mapping.update_resource_atx(X,Y,0);
		mapping.get_nearest('r',XN,YN,'G');
		rover.ia.get_map_size(Width, Height);
		if(XN == Width & YN == Height){
			!explore;
		}
		else{
			!wait_at_resource(XN,YN);
		}
		.

@resource_found(RsType, Qty, XDist, YDist)[atomic]
+ resource_found(RsType, Qty, XDist, YDist) : true
	<-	.print("RESOURCE FOUND (XDist : ",XDist," YDist :",YDist,")");
	+scan_success;
	mapping.get_loc(X,Y);
	mapping.new_resource(X+XDist,Y+YDist,RsType,Qty,Exists);
	!handle_resource_found(RsType,Qty,XDist,YDist,Exists);
	-resource_found(RsType, Qty, XDist, YDist);	
    .

+! handle_resource_found(RsType,Qty,XDist,YDist,Exists)
	<-rover.ia.check_config(Capacity,Scanrange,Resourcetype);
	.print("RsType: ",RsType," Resourcetype: ",Resourcetype);
	if (collected[source(collector1)] & RsType == Resourcetype & Exists == -1){
		-collected[source(collector1)];
		.print("telling collector to pickup");
		.send(collector1,achieve,pickup);
	}
	elif(returning_to_base[source(collector1)]& RsType == Resourcetype & Exists == -1){
		-returning_to_base[source(collector1)];
		mapping.get_nearest('a',X,Y,'G');
		.print("telling collector to collect");
		.send(collector1,acheive,collect(X,Y));
	}
	elif(RsType == Resourcetype){
		?waitingrs(Q);
		-+waitingrs(Q+1);	
	}
	.

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

	