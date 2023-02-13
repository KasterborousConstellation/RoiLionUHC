package roilionuhc.roilionuhc.role;

import fr.supercomete.head.GameUtils.Time.TimeUtility;
import fr.supercomete.head.Inventory.InventoryUtils;
import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.CoolDown;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleHandler;
import fr.supercomete.head.role.RoleModifier.HasAdditionalInfo;
import fr.supercomete.head.role.Triggers.Trigger_OnInteractWithUUIDItem;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import fr.supercomete.nbthandler.NbtTagHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Pumba extends Role implements HasAdditionalInfo,Trigger_WhileAnyTime, Trigger_OnInteractWithUUIDItem {
    final CoolDown coolDown = new CoolDown(0,60*10);
    public Pumba(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[]{};
    }

    @Override
    public String askName() {
        return "Pumba";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.LionEarth;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet résistance permanent.",
                "Vous avez un CD nommé 'Hakuna Matata' qui vous donne l'effet résistance 2 pendant 10s (1x/10min)"
        );
    }

    @Override
    public ItemStack[] askItemStackgiven() {
        ItemStack stack = InventoryUtils.getItem(Material.RECORD_10,"Hakuna Matata",null);
        stack = NbtTagHandler.createItemStackWithUUIDTag(stack,4503);
        return new ItemStack[]{stack};
    }

    @Override
    public boolean AskIfUnique() {
        return true;
    }

    @Override
    public String AskHeadTag() {
        return null;
    }

    @Override
    public void WhileAnyTime(Player player) {
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,0,20*3));
    }

    @Override
    public void OnInteractWithUUIDItem(Player player, int i, PlayerInteractEvent playerInteractEvent) {
        if(i==4503){
            if(coolDown.isAbleToUse()){
                coolDown.setUseNow();
                RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,1,11*20));
                for(Player other : Bukkit.getOnlinePlayers()){
                    if(RoiLionUHC.api.getPlayerHelper().IsPlayerInGame(other.getUniqueId())){
                        if(RoleHandler.getRoleOf(other) instanceof Timon){
                            RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(other,new KTBSEffect(PotionEffectType.INCREASE_DAMAGE,0,2*60*20));
                            other.sendMessage("§7Pumba vient d'activer §aHakuna Matata§7 vous avez donc l'effet force pendant 2minutes.");
                        }
                    }
                }
            }else{
                player.sendMessage("§cVous ne pouvez pas utlisé ce pouvoir pour l'instant: "+ TimeUtility.transform(coolDown.getRemainingTime(),"§6")+" avant de pouvoir réutiliser cet objet.");
            }
        }
    }

    @Override
    public String[] getAdditionnalInfo() {
        return new String[]{"Vous connaissez l'identité de Timon: "+RoiLionUHC.api.getRoleProvider().FormalizedWhoHaveRole(Timon.class)};
    }
}
