package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
  		BSTNode<T> pivot = (BSTNode<T>) node.getRight();
  		
  		node.setRight(pivot.getLeft());
  		pivot.getLeft().setParent(node);
  		
  		BSTNode<T> parent = (BSTNode<T>) node.getParent();
  		pivot.setParent(parent);
  		
  		if (parent != null) {
  			if (parent.getLeft() == node)
  				parent.setLeft(pivot);
  			else
  				parent.setRight(pivot);
  		}
  		
  		node.setParent(pivot);
  		pivot.setLeft(node);
  		
  		return pivot;
  	}                                                                                                         
  
  	/**
  	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
  	 * @param node
  	 * @return
  	 */
  	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
  		BSTNode<T> pivot = (BSTNode<T>) node.getLeft();
  		
  		node.setLeft(pivot.getRight());
  		pivot.getRight().setParent(node);
  		
  		BSTNode<T> parent = (BSTNode<T>) node.getParent();
  		pivot.setParent(parent);
  		
  		if (parent != null) {
  			if (parent.getLeft() == node)
  				parent.setLeft(pivot);
  			else
  				parent.setRight(pivot);
  		}
  		
  		node.setParent(pivot);
  		pivot.setRight(node);
  		
  		return pivot;
  	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
