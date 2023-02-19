package roilionuhc.roilionuhc;

import fr.supercomete.head.GameUtils.GUI.GUI;
import fr.supercomete.head.GameUtils.Time.TimeUtility;
import fr.supercomete.head.Inventory.InventoryUtils;
import fr.supercomete.head.core.Main;
import fr.supercomete.head.role.Bonus.Bonus;
import fr.supercomete.head.role.Bonus.BonusType;
import fr.supercomete.head.role.RoleHandler;
import fr.supercomete.head.role.RoleState.RoleStateTypes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import roilionuhc.roilionuhc.role.Rafiki;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class CocoGUI extends GUI {
    private static final CopyOnWriteArrayList<CocoGUI> allGui = new CopyOnWriteArrayList<>();
    private Inventory inv;
    private Player player;
    private Rafiki rafiki;
    public CocoGUI() {
        this.player=null;
    }
    public CocoGUI(Player player,Rafiki rafiki) {
        this.player=null;
        this.rafiki=rafiki;
        this.player=player;
        if (player != null)
            allGui.add(this);
    }
    protected Inventory generateinv() {
        Inventory tmp = Bukkit.createInventory(null, 9,"Rafiki");
        ItemStack orange = InventoryUtils.createColorItem(Material.INK_SACK,"Orange",1,(short) 14);
        ItemMeta meta = orange.getItemMeta();
        meta.setLore(Collections.singletonList("§r§c10% de vitesse pendant 30s."));
        orange.setItemMeta(meta);
        tmp.setItem(3,orange);
        ItemStack rouge = InventoryUtils.createColorItem(Material.INK_SACK,"Rouge",1,(short) 1);
        meta = rouge.getItemMeta();
        meta.setLore(Collections.singletonList("§r§c10% de force pendant 30s."));
        rouge.setItemMeta(meta);
        tmp.setItem(4,rouge);
        ItemStack jaune = InventoryUtils.createColorItem(Material.INK_SACK,"Rouge",1,(short) 11);
        meta = jaune.getItemMeta();
        meta.setLore(Collections.singletonList("§r§c10% de résistance pendant 30s."));
        jaune.setItemMeta(meta);
        tmp.setItem(5,jaune);
        return tmp;
    }
    public void open() {
        this.inv = generateinv();
        player.openInventory(inv);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        for (CocoGUI gui:allGui) {
            if (e.getInventory().equals(gui.inv)) {
                e.setCancelled(true);
                int currentSlot = e.getSlot();
                ClickType action = e.getClick();
                switch (currentSlot) {
                    default:
                        Bonus[] bonuses = new Bonus[]{new Bonus(BonusType.Speed,10),new Bonus(BonusType.Force,10),new Bonus(BonusType.Damage_Resistance,10)};
                        if(currentSlot>2&&currentSlot<6){
                            int index = currentSlot-3;
                            if(gui.rafiki.coolDown.isAbleToUse()){
                                gui.rafiki.coolDown.setUseNow();
                                gui.rafiki.addBonus(new Bonus(bonuses[index].getType(),bonuses[index].getLevel()));
                                gui.player.closeInventory();
                                gui.player.sendMessage("Vous avez utilisé une de vos noix.");
                                new BukkitRunnable(){
                                    @Override
                                    public void run() {
                                        gui.rafiki.addBonus(new Bonus(bonuses[index].getType(),-bonuses[index].getLevel()));
                                        if(gui.player!=null && gui.player.isOnline()){
                                            gui.player.sendMessage("L'effet de votre noix disparait.");
                                        }
                                    }
                                }.runTaskLater(RoiLionUHC.INSTANCE,20*30);
                            }else{
                                gui.player.sendMessage("Veuillez attendre encore: "+ TimeUtility.transform(gui.rafiki.coolDown.getRemainingTime(),"§c"));
                            }
                        }
                        break;
                }
            }
        }
    }
    // Optimization --> Forget GUI that have been closed >|<
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        allGui.removeIf(gui -> e.getInventory().equals(gui.inv));
    }
}
