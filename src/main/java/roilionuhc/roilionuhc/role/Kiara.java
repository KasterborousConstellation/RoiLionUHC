package roilionuhc.roilionuhc.role;

import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleHandler;
import fr.supercomete.head.role.RoleModifier.PreAnnouncementExecute;
import fr.supercomete.head.role.Triggers.Trigger_WhileDay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.*;

public class Kiara extends Role implements Trigger_WhileDay, PreAnnouncementExecute {
    public String kovu;
    UUID chosen;
    public Kiara(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        if(kovu==null) {
            return new String[]{"Vous connaissez le nom d'une lionne aléatoire: "+RoiLionUHC.api.getRoleProvider().getRoleOf(chosen)};
        }else{
            return new String[]{"Vous connaissez le nom d'une lionne aléatoire: "+RoiLionUHC.api.getRoleProvider().getRoleOf(chosen),"§7Kovu est "+kovu};
        }
    }

    @Override
    public String askName() {
        return "Kiara";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.LionEarth;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(   "Vous avez l'effet résistance pendant le jour.",
                                "Si Kovu rejoint votre camp, vous recevez son pseudo"
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
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,0,20*4));
    }

    @Override
    public void PreAnnouncement() {
        ArrayList<UUID> ids = new ArrayList<>();
        for(final Role role : RoleHandler.getRoleList().values()){
            if(role instanceof Lionnes){
                ids.add(role.getOwner());
            }
        }
        if(ids.size()>0){
            if(ids.size()==1){
                chosen = ids.get(0);
            }else{
                chosen = ids.get(new Random().nextInt(ids.size()));
            }
        }
    }
}
