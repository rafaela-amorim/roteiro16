package adt.rbtree;

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
		boolean answer = true;
		
		
		
		return answer;
	}

	/**
	 * Verifies the black-height property from the root.
	 */
	private boolean verifyBlackHeight() {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public void insert(T value) {
		if (value != null) {
			insert(value);
			RBNode<T> node = (RBNode<T>) search(value);
			node.setColour(Colour.RED);
			
			
		}
	}

	@Override
	public RBNode<T>[] rbPreOrder() {
		// TODO Implement your code here
		throw new UnsupportedOperationException("Not implemented yet!");
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












