package roilionuhc.roilionuhc;

import fr.supercomete.enums.Gstate;
import fr.supercomete.head.structure.Structure;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceAndDestroyEvent implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(RoiLionUHC.api.getGameProvider().getCurrentGame().getGamestate()!=Gstate.Waiting){
            Structure structure = RoiLionUHC.api.getGameProvider().getCurrentGame().getMode().getStructure().get(0);
            if(e.getBlockPlaced().getLocation().getWorld().equals(structure.getLocation().getWorld())&&e.getBlockPlaced().getLocation().distance(structure.getLocation())<150){
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onBlockPlace(BlockBreakEvent e){
        if(RoiLionUHC.api.getGameProvider().getCurrentGame().getGamestate()!=Gstate.Waiting){
            Structure structure = RoiLionUHC.api.getGameProvider().getCurrentGame().getMode().getStructure().get(0);
            if(e.getBlock().getLocation().getWorld().equals(structure.getLocation().getWorld())&&e.getBlock().getLocation().distance(structure.getLocation())<150){
                e.setCancelled(true);
            }
        }
    }
}
