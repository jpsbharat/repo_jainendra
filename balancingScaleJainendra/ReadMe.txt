Overview
	A file is read from System.in that represents a tower of weighting scales. Each scale has a mass of 1kg in it's own right and has two
	pans, one on the left and on the right. One scale is on the ground and holds one it's left
	and right pan either:
		1. another scale,
		2. a mass (in kgs), or
		3. a mass and another scale.
		
	The program needs to read in the file representing this scale tree, and determine what
	minimal set of masses to add to each side of each scale so that the scale tree is
	perfectly balanced.
	
	Detail
		Input from System.in
		
		1. Some lines start with a #, these are comments and should be ignore
	    2. Lines are comma separated with 3 fields: a) the scale name (string identifier starting
		   with [Aa-Za]; b) the mass or scale on the left side; and c) the mass or scale on the
		   right side. Masses are a single number representing the mass in kilograms. Other scales
		   are identified by their scale name, and can be distinguished from masses in that they
		   start with [Aa-Za] while masses are a simple integer number.
		   
	Example Input
	#ScaleName,Left,Right
	B1,10,B2
	B2,B3,4
	B3,7,8
	
	This represents a scale B1 at the bottom of the tree. On it's left pan it has a 10kg mass. On it's right pan it has another scale B2. B2 has yet another scale B3 on it's left pan, and a 4kg mass on it's right pan. B3 has a 7kg mass on it's left pan and an 8kg mass on it's right pan.
	Output to System.out
	The code needs to output what masses need to be place on the left and right side of each scale to ensure a balanced tree of scales (pans can hold both a mass and a scale on each pan.) Each output line should be a) the scale name, b) additional mass to place on left pan, c) additional mass to place on right pan.
		
	Example output
	B1,25,0
	B2,0,13
	B3,1,0
	
    This implies that we should add 1 kg to the left pan of scale B3, 13kg to the right of scale B2 and 25kg to the left pan of scale B1 to balance the scale tree. The scales should be output in alphebetic order [A...Z and 0..9], which will be the same order as they are presented in the input.