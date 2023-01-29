package roilionuhc.roilionuhc.role;
import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.Triggers.Trigger_WhileDay;
import fr.supercomete.head.role.Triggers.Trigger_onNightTime;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
public class Ed extends Role implements Trigger_WhileDay, Trigger_onNightTime {
    public boolean crie = false;
    public Ed(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Ed";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Hyennes;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet §cforce§7 durant le jour.",
                "Vous avez la commande '/rlu crie' qui vous permet d'annuler les effets des autres joueurs dans un rayons de 50blocs un fois dans la partie."
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
    public void WhileDay(Player player) {
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.INCREASE_DAMAGE,0,60));
    }

    @Override
    public void onNightTime(Player player) {
        if(player.getHealth()>=3){
            player.setHealth(player.getHealth()-2);
        }else if(player.getHealth()>1){
            player.setHealth(1);
        }
    }
}