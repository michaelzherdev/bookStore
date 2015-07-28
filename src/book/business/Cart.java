package book.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<LineItem> items;
	
	public Cart(){
		items = new ArrayList<LineItem>();
	}

	public List<LineItem> getItems() {
		return items;
	}

	public void setItems(List<LineItem> items) {
		this.items = items;
	}
	
	public int getCount(){
		return items.size();
	}
	
	public void addItem(LineItem item){
		//If the item already exists in the cart, only the quantity is changed.
		String code = item.getProduct().getISBN();
		int quantity = item.getQuantity();
		for(int i = 0; i < items.size(); i++){
			LineItem lineItem = items.get(i);
			if(lineItem.getProduct().getISBN().equals(code)){
				lineItem.setQuantity(quantity);
				return;
			}
		}
		items.add(item);
	}
	
	public void removeItem(LineItem item){
		String code = item.getProduct().getISBN();
		for(int i = 0; i < items.size(); i++){
			LineItem lineItem = items.get(i);
			if(lineItem.getProduct().getISBN().equals(code)){
				items.remove(i);
				return;
			}
		}
	}
}
