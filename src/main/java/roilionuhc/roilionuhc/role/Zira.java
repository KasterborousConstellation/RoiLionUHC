package roilionuhc.roilionuhc.role;

import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.Bonus.Bonus;
import fr.supercomete.head.role.Bonus.BonusType;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import fr.supercomete.head.role.Triggers.Trigger_WhileDay;
import fr.supercomete.head.role.Triggers.Trigger_onEpisodeTime;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Zira extends Role implements Trigger_onEpisodeTime, Trigger_WhileAnyTime , Trigger_WhileDay {
    boolean given= false;
    public Zira(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Zira";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Rejected;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList("A la mort de Scar vous obtenez 3♥coeurs en plus ainsi que force pendant le jour.",
                "Tant que Kovu gagne avec vous, vous gagnez 1♥ tout les épisodes (Max 15♥)",
                "Vous avez l'effet résistance pendant la nuit.");
    }

    @Override
    public ItemStack[] askItemStackgiven() {
        return new ItemStack[0];
    }

    @Override
    public boolean AskIfUnique() {
        return false;
    }

    @Override
    public String AskHeadTag() {
        return null;
    }

    @Override
    public void onEpisodeTime(Player player) {
        UUID kovu = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Kovu.class);
        if(kovu!=null){
            Role role = RoiLionUHC.api.getRoleProvider().getRoleOf(kovu);
            if(role.getCamp().equals(RLTeams.Rejected)){
                role.addBonus(new Bonus(BonusType.Heart,2));
                player.sendMessage("§aKovu gagne avec vous, vous avez 1 ♥ en plus. (Max 15)");
            }
        }
    }

    @Override
    public void WhileAnyTime(Player player) {
        if(!given){
            if(RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Scar.class)==null){
                given=true;
                player.sendMessage("§aScar est mort, vous gagnez 3♥ (Max 15) ainsi que force pendant le jour.");
                addBonus(new Bonus(BonusType.Heart,6));
            }
        }
        double maxheart = player.getMaxHealth();
        if(maxheart>30){
            addBonus(new Bonus(BonusType.Heart,(int)maxheart-30));
        }
    }

    @Override
    public void WhileDay(Player player) {
        if(given){
            RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.INCREASE_DAMAGE,0,20*3));
        }
    }
}
