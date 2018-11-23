package adt.rbtree;

import java.util.ArrayList;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T>
		implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return blackHeight((RBNode<T>) root);
	}
	
	protected int blackHeight(RBNode<T> node) {
		int height = 0;
		
		if (node.isEmpty()) {
			height = 0;
		} else if (node.getColour() == Colour.BLACK) {
			height = 1 + blackHeight((RBNode<T>) node.getLeft());
		} else {
			height = blackHeight((RBNode<T>) node.getLeft());
		}
		
		return height;
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour()
				&& verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {		
		return verifyChildrenOfRedNodes((RBNode<T>) root);
	}

	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		boolean answer = true;
		
		if (!node.isEmpty()) {
			if (node.getColour() == Colour.RED) {
				if (((RBNode<T>) node.getLeft()).getColour() != Colour.BLACK || 
					((RBNode<T>) node.getRight()).getColour() != Colour.BLACK) 
				{
					answer = false;
				} 
			}
			
			if (answer) {
				answer = verifyChildrenOfRedNodes((RBNode<T>) node.getLeft()) && 
				    	 verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
			}
		}
		
		return answer;
	}
	
	/**
	 * Verifies the black-height property from the root.
	 */
	private boolean verifyBlackHeight() {		
		return verifyBlackHeight((RBNode<T>) root, 0, blackHeight());
	}
	
	private boolean verifyBlackHeight(RBNode<T> node, int contador, int size) {
		boolean answer = true;
		
		if (!node.isEmpty()) {
			if (node.getColour() == Colour.BLACK) {
				answer = verifyBlackHeight((RBNode<T>) node.getLeft(), 1 + contador, size) && verifyBlackHeight((RBNode<T>) node.getRight(), 1 + contador, size);
			} else {
				answer = verifyBlackHeight((RBNode<T>) node.getLeft(), contador, size) && verifyBlackHeight((RBNode<T>) node.getRight(), contador, size);
			}
		} else {
			answer = (contador == size);
		}
		
		return answer;
	}

	@Override
	public void insert(T value) {
		if (value != null) {
			insert(root, value);
		}
	}
	
	@Override
	protected void insert(BSTNode<T> node, T elem) {
		node = (RBNode<T>) node;
		
		if (node.isEmpty()) {
			node.setData(elem);
			node.setLeft(new RBNode<>());
			node.setRight(new RBNode<>());

			node.getLeft().setParent(node);
			node.getRight().setParent(node);
			
			((RBNode<T>) node).setColour(Colour.RED);
			fixUpCase1((RBNode<T>) node);
		} else {
			if (node.getData().compareTo(elem) > 0)
				insert((RBNode<T>) node.getLeft(), elem);
			else
				insert((RBNode<T>) node.getRight(), elem);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RBNode<T>[] rbPreOrder() {
		ArrayList<RBNode<T>> list = new ArrayList<>();
		rbPreOrder((RBNode<T>) root, list);
		RBNode<T>[] answer = list.toArray(new RBNode[list.size()]);
		return answer;
	}
	
	private void rbPreOrder(RBNode<T> node, ArrayList<RBNode<T>> list) {
		if (!node.isEmpty()) {
			list.add(node);
			rbPreOrder((RBNode<T>) node.getLeft(), list);
			rbPreOrder((RBNode<T>) node.getRight(), list);
		}
	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (!node.isEmpty()) {
			if (node == root) {
				node.setColour(Colour.BLACK);
			} else {
				fixUpCase2(node);
			}
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		if (((RBNode<T>) node.getParent()).getColour() != Colour.BLACK) {
			fixUpCase3(node);
		}
	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> grandParent = (RBNode<T>) node.getParent().getParent();
		RBNode<T> uncle = (RBNode<T>) grandParent.getLeft();
		
		if (node.getParent() == grandParent.getLeft()) {
			uncle = (RBNode<T>) grandParent.getRight();
		}
		
		if (uncle.getColour() == Colour.RED) {
			((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
			uncle.setColour(Colour.BLACK);
			grandParent.setColour(Colour.RED);
			fixUpCase1(grandParent);
		} else {
			fixUpCase4(node);
		}
		
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;
		RBNode<T> parent = (RBNode<T>) node.getParent();
		
		if (parent.getRight() == node && parent.getParent().getLeft() == parent) {
			Util.leftRotation(node);
			next = (RBNode<T>) node.getLeft();
		} else if (parent.getLeft() == node && parent.getParent().getRight() == parent) {
			Util.rightRotation(node);
			next = (RBNode<T>) node.getRight();
		}
		
		fixUpCase5(next);
	}

	protected void fixUpCase5(RBNode<T> node) {
		RBNode<T> grandParent = (RBNode<T>) (node.getParent()).getParent();
		
		((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
		grandParent.setColour(Colour.RED);
		
		if (node.getParent().getLeft() == node) {
			Util.rightRotation(grandParent);
		} else {
			Util.leftRotation(grandParent);
		}
	}
}












