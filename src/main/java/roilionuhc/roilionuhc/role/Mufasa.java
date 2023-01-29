package roilionuhc.roilionuhc.role;

import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleHandler;
import fr.supercomete.head.role.Triggers.Trigger_OnAnyKill;
import fr.supercomete.head.role.Triggers.Trigger_OnKill;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Mufasa extends Role implements Trigger_OnKill, Trigger_WhileAnyTime, Trigger_OnAnyKill {
    public Mufasa(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Mufasa";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.LionEarth;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "En dessous de 5♥ vous avez l'effet résistance.",
                "A la mort de Simba vous obtenez le pseudo de Sarabi.",
                "Si vous tuez 2 membres de votre camp, alors vous serez révélé au grand jour."
        );
    }

    @Override
    public ItemStack[] askItemStackgiven() {
        return new ItemStack[0];
    }

    @Override
    public boolean AskIfUnique() {
        return true;
    }

    @Override
    public String AskHeadTag() {
        return null;
    }
    int kill=0;
    @Override
    public void onKill(Player player, Player killed) {
        Role role = RoleHandler.getRoleOf(killed);
        if(role!=null && role.getCamp()==RLTeams.LionEarth){
            kill++;
        }
        if(kill==2){
            Bukkit.broadcastMessage("§6Mufasa§7 a tué deux membres de son camp. Son identité est donc révélée: §c"+player.getName());
        }
    }

    @Override
    public void WhileAnyTime(Player player) {
        if(player.getHealth()<10){
            RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player, new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,0,60));
        }
    }

    @Override
    public void onOtherKill(Player player, Player killer) {
        Role role = RoleHandler.getRoleOf(player);
        if(role instanceof Simba){
            Player ths=Bukkit.getPlayer(this.getOwner());
            ths.sendMessage("§7Simba est mort, vous obtenez le pseudo de Sarabi: §6"+RoiLionUHC.api.getRoleProvider().FormalizedWhoHaveRole(Sarabi.class));
        }
    }
}