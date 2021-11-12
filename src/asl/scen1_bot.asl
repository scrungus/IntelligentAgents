

! explore.


+! explore : true
   <- move(2,1);
      rover.ia.log_movement(2,1);
      scan(3);
      .
      
+! pickupdropoff(Qty) : true
	<- rover.ia.check_config(Capacity,Scanrange,Resourcetype)
	if (Qty <= Capacity){
      	for ( .range(I,1,Qty) ) {
      		collect("gold")
      	}
      	rover.ia.get_distance_from_base(XD, YD);
      	move(XD,YD);
      	rover.ia.log_movement(XD,YD);
      	!deposit0(Qty)
      }
      else{
      	for ( .range(I,1,Capacity) ) {
      		collect("gold")
      	}
      	rover.ia.get_distance_from_base(XD, YD);
      	move(XD,YD);
      	rover.ia.log_movement(XD,YD);
      	!deposit0(Capacity)   	
      }.
      
+! deposit0(Qty) : true
  <-for ( .range(I,1,Qty) ) {
      		deposit("gold")
      	}.
      
 + resource_not_found : true
    <- !explore.
    
    
+ resource_found(RsType, Qty, XDist, YDist) : true
   <- move(XDist, YDist);
      rover.ia.log_movement(XDist, YDist);
      !pickupdropoff(Qty)
      !explore
      .
        
+invalid_action(collect,unmet_requirement) : true
	<- !explore.
 
