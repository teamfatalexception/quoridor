public class Client {
    public static void main (String [] args) {
        DisjSets d = new DisjSets(8);
	System.out.println("d = " + d);
        for (int i = 0; i <=7; i+=2) {
            int root1 = d.find(i);
            int root2 = d.find(i+1);
	    if (root1 != root2)
		d.union(root1, root2);
	}
	System.out.println("d = " + d);

        int root1 = d.find(0);
        int root2 = d.find(2);
	if (root1 != root2)
	    d.union(root1, root2);

        root1 = d.find(4);
        root2 = d.find(6);
	if (root1 != root2)
	    d.union(root1, root2);
	System.out.println("d = " + d);


        root1 = d.find(3);
	root2 = d.find(4);
	System.out.println("root1 = " + root1 + " root2 = " + root2);
	System.out.println("d = " + d);
	if (root1 != root2)
	   d.union(root1, root2);
	System.out.println("d = " + d);


    }
}