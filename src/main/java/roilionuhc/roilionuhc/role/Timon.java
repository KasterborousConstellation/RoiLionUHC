package roilionuhc.roilionuhc.role;

import fr.supercomete.head.Inventory.InventoryUtils;
import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleModifier.HasAdditionalInfo;
import fr.supercomete.head.role.Triggers.Trigger_OnInteractWithUUIDItem;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import fr.supercomete.head.role.Triggers.Trigger_onEpisodeTime;
import fr.supercomete.nbthandler.NbtTagHandler;
import org.bukkit.Location;
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

public class Timon extends Role implements HasAdditionalInfo,Trigger_OnInteractWithUUIDItem, Trigger_WhileAnyTime, Trigger_onEpisodeTime {
    boolean used =false;
    public Timon(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[]{};
    }

    @Override
    public String askName() {
        return "Timon";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.LionEarth;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet §bvitesse§7 permanent.",
                "Vous avez une plume qui vous donne Jump Boost 4 pendant 1m30 (1x/épisode)",
                "Si Pumba utilise 'Hakuna Matata', vous obtiendrez l'effet force pendant 2min."
        );
    }

    @Override
    public ItemStack[] askItemStackgiven() {
        ItemStack stack = InventoryUtils.getItem(Material.FEATHER,"§aJump Boost",null);
        stack = NbtTagHandler.createItemStackWithUUIDTag(stack,4502);
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
    public void OnInteractWithUUIDItem(Player player, int i, PlayerInteractEvent playerInteractEvent) {
        if(i==4502){
            if(!used){
                used=true;
                RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.JUMP,3,90*20));
                player.sendMessage("§7Vous venez d'activer votre Jump Boost");
            }else{
                player.sendMessage("§7Vous avez déjà utilisé votre Jump Boost cet épisode.");
            }
        }
    }

    @Override
    public void WhileAnyTime(Player player) {
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.SPEED,0,999999999));
    }

    @Override
    public void onEpisodeTime(Player player) {
        if(used){
            player.sendMessage("§aVous pouvez de nouveau utiliser votre Jump Boost.");
            used=false;
        }
    }

    @Override
    public String[] getAdditionnalInfo() {
        return new String[]{"Vous connaissez l'identité de Pumba: "+RoiLionUHC.api.getRoleProvider().FormalizedWhoHaveRole(Pumba.class)};
    }
}
