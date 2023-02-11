package roilionuhc.roilionuhc.role;

import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.RoleModifier.HasAdditionalInfo;
import fr.supercomete.head.role.RoleModifier.PreAnnouncementExecute;
import fr.supercomete.head.role.Triggers.Trigger_WhileNight;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;

import java.util.*;

public class Hyenes extends Role implements Trigger_WhileNight, PreAnnouncementExecute , HasAdditionalInfo {
    UUID id;
    public Hyenes(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Hyene";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.Hyennes;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez l'effet §cforce§7 pendant la nuit"
        );
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
    public void WhileNight(Player player) {
        RoiLionUHC.api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.INCREASE_DAMAGE,0,20*3));
    }

    @Override
    public void PreAnnouncement() {
        UUID banzai = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Banzai.class);
        UUID ed = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Banzai.class);
        UUID Shenzi = RoiLionUHC.api.getRoleProvider().getWhoHaveRole(Banzai.class);
        ArrayList<UUID> ids = new ArrayList<>();
        if(banzai!=null){
            ids.add(banzai);
        }
        if(ed!=null){
            ids.add(ed);
        }
        if(Shenzi!=null){
            ids.add(Shenzi);
        }
        id = ids.get(ids.size()==1?0:new Random().nextInt(ids.size()));
    }

    @Override
    public String[] getAdditionnalInfo() {
        return new String[]{"Vous connaissez Ed,Shenzi ou Banzai: "+RoiLionUHC.api.getPlayerHelper().getNameFromUUID(id)};
    }
}