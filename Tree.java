import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Tree {
  private final String LEFT = "left";
  private final String RIGHT = "right";
  private final String OTHER = "other";
  private final int LEFT_ID = 0;
  private final int RIGHT_ID = 1;
  private final int OTHER_ID = 2;
  private final int MAX_NODE = 100;
  private static final int MAX_VALUE = 100;

  private Map<Integer, String> instructionMap;
  
  private Random rand = new Random();

  public Tree() {
    instructionMap = new HashMap<Integer, String>();
    instructionMap.put(LEFT_ID, LEFT);
    instructionMap.put(RIGHT_ID, RIGHT);
    instructionMap.put(OTHER_ID, OTHER);

    rand = new Random();
  }

  public TreeNode createTree() {
    //len: number of nodes in the tree
    int len = rand.nextInt(MAX_NODE);

    TreeNode root = null;
    TreeNode cur = root;

    //ids: keep the nodes whose one of the children is null for random id selection
    List<TreeNode> nodes = new ArrayList<TreeNode>();
    int id = 0;

    for(int i = 0; i<len; i++) {
      //randomly generate an id and assign a treenode to it
      id = rand.nextInt(MAX_VALUE);
      TreeNode node = new TreeNode(id);
      
      if(root == null) {
        root = node;
        cur = root;
      }
      else {
        /*3 possible instructions: append new node to left or right of cur or another node. This spreads all nodes more evenly accross the tree
          Ensure instruction follows that 
          1. it does not instruct to override any existing node. If the left side is not null, choose the right or another node. Likewise for right side.
          2. it does not randomly select other nodes if nodes(List) is empty
          3. If 3 options are available, randomly select one of them
        */
        String instruction;

        if(cur.left != null && cur.right != null) {
          instruction = instructionMap.get(OTHER_ID);
        }
        else if(cur.left != null) {
          instruction = instructionMap.get( rand.nextInt(100) > 50 ? RIGHT_ID : OTHER_ID );
        }
        else if(cur.right != null) {
          instruction = instructionMap.get( rand.nextInt(100) > 50 ? LEFT_ID : OTHER_ID );
        }
        else {
          if(nodes.size() == 0) {
            instruction = instructionMap.get( rand.nextInt(2) );
          }
          else {
            instruction = instructionMap.get( rand.nextInt(3) );
          }
        }

        //execute the instruction
        switch(instruction) {
          case LEFT:
            cur.left = node;
            break;
  
          case RIGHT:
            cur.right = node;
            break;
  
          case OTHER:
            //choose a random node whose one of the children is null from nodes(ArrayList)
            cur = nodes.get( rand.nextInt(nodes.size()) );
            
            /* ensure the instruction follows that
             * 1. if any side is not null, choose the other non-null side
             * 2. if 2 sides are null, randomly select a side
             */
            if(cur.left != null) {
              instruction = instructionMap.get( RIGHT_ID );
            }
            else if(cur.right != null) {
              instruction = instructionMap.get( LEFT_ID );
            }
            else {
                instruction = instructionMap.get( rand.nextInt(2) );
            }
            
            //execute the instruction
            switch(instruction) {
              case LEFT:
                cur.left = node;
                break;
      
              case RIGHT:
                cur.right = node;
                break;
            }
        }
      }
      
      //remove a node whose 2 children are no longer null
      if(cur.left != null && cur.right != null) {
        nodes.remove(cur);
      }

      //add a node whose 2 children are null;
      nodes.add(node);
      
      cur = nodes.get( rand.nextInt(nodes.size()) );
    }

    return root;
  }

  private TreeNode createBalancedTree() {
    return null;
  }

  public void printTree(String space, TreeNode root) {
    if(root == null) return;

    System.out.println(space + root.val);

    printTree(space + " ", root.left);
    printTree(space + " ", root.right);
  }
}
