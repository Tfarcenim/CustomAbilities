package tfar.customabilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import javax.annotation.Nullable;
import java.util.*;

public class Constants {

	public static void addStackableEffect(LivingEntity living,MobEffectInstance instance) {
		if (living.hasEffect(instance.getEffect())) {
			Map<MobEffect, MobEffectInstance> activeEffectsMap = living.getActiveEffectsMap();
			MobEffectInstance existing = activeEffectsMap.get(instance.getEffect());

			if (existing.isInfiniteDuration()) return;
			if (existing.getAmplifier() != instance.getAmplifier()) return;

			MobEffectInstance newEffect = new MobEffectInstance(instance.getEffect(),instance.getDuration() + existing.getDuration(),
					instance.getAmplifier(),instance.isAmbient(),instance.isVisible());
			living.addEffect(newEffect);
		} else {
			living.addEffect(instance);
		}
	}


	public static final ResourceLocation LUTE_RL = new ResourceLocation("immersive_melodies","lute");
	public static final ResourceLocation GUITAR_RL = new ResourceLocation("xercamusic","redstone_guitar");


	public static boolean triggerEvent(Level level, double x,double y,double z) {
		NoteBlockInstrument instrument = NoteBlockInstrument.values()[level.getRandom().nextInt(NoteBlockInstrument.values().length)];
		float $$7;
		if (instrument.isTunable()) {
			List<Integer> values = new ArrayList<>(NoteBlock.NOTE.getPossibleValues());
			int note = values.get(level.random.nextInt(values.size()));
			$$7 = NoteBlock.getPitchFromNote(note);
			level.addParticle(ParticleTypes.NOTE, x + 0.5, y+ 1.2, z + 0.5, note / 24.0, 0.0, 0.0);
		} else {
			$$7 = 1.0F;
		}

		Holder<SoundEvent> $$10;
		if (instrument.hasCustomSound()) {
			ResourceLocation $$9 = getCustomSoundId(level, new BlockPos((int) x,(int) y,(int) z));
			if ($$9 == null) {
				return false;
			}

			$$10 = Holder.direct(SoundEvent.createVariableRangeEvent($$9));
		} else {
			$$10 = instrument.getSoundEvent();
		}

		level.playSeededSound(
				null, x + 0.5, y + 0.5, z + 0.5, $$10, SoundSource.RECORDS, 3.0F, $$7, level.random.nextLong()
		);
		return true;
	}

	@Nullable
	private static ResourceLocation getCustomSoundId(Level $$0, BlockPos $$1) {
		BlockEntity var4 = $$0.getBlockEntity($$1.above());
		return var4 instanceof SkullBlockEntity $$2 ? $$2.getNoteBlockSound() : null;
	}


}