package shop.View;

import shop.Model.Item;

// Basic interface that can be used to listen to any kind of
// event that results in an item being passed around.
public interface ItemListener {
    public abstract void onItem(Item item);
}
