package roilionuhc.roilionuhc.role;
import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.Bonus.Bonus;
import fr.supercomete.head.role.Bonus.BonusType;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleHandler;
import fr.supercomete.head.role.Triggers.Trigger_OnAnyKill;
import fr.supercomete.head.role.Triggers.Trigger_WhileDay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Sarabi extends Role implements Trigger_OnAnyKill, Trigger_WhileDay {
    public boolean exchange=false;
    public Sarabi(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Sarabi";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.LionEarth;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet §cforce§7 pendant le jour",
                "Vous pouvez connaitre l'identité de Simba a la condition de perdre 2 ♥ permanents, avec la commande /rlu simba.",
                "Si vous avez choisi de connaître le pseudo de Simba vous regagnerez un ♥ à la mort de Mufasa."
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

    @Override
    public void onOtherKill(Player player, Player player1) {
        Role role = RoleHandler.getRoleOf(player);
        if(role instanceof Mufasa){
            if(exchange){
                addBonus(new Bonus(BonusType.Heart,2));
            }
        }
    }

    @Override
    public void WhileDay(Player player) {
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.INCREASE_DAMAGE,0,20*4));
    }
}