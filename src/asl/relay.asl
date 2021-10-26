/**
	Relay Agent
	This agent relays messages to agents of the same group
	and may be used in scenario 5 for group broadcast

	NOTE: This agent does not count towards the agent limit for the scenario when used but may 
	be left out if you don not wish to utilise this feature.
**/


@ register_as_group_member(_)[atomic]
+ register_as_group_member(Group)[source(Agent)]
	: true
	<-  + group_member_registry(Group,Agent);
	 	.print('....').
	

@ group_broadcast(_,_)[atomic]
+ group_broadcast(Group,Information)[Source(Agent)]
	: true
	<- .print('.....').


