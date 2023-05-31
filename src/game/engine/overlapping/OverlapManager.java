package game.engine.overlapping;

import game.engine.smart_register.LivingSubscriber;
import game.engine.smart_register.SmartSubscribing;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class OverlapManager {
    public void registerOverlappingObject(LivingSubscriber<? extends OverlappingObject> object, OverlapFactor tag) {
        Subscriptions.get(tag).register(object);
    }

    public void update() {
        var tags = OverlapFactor.values();
        for (int i = 0; i < tags.length; ++i) {
            for (int j = i + 1; j < tags.length; ++j) {
                overlap(tags[i], tags[j]);
            }
        }
    }

    private void overlap(OverlapFactor tag1, OverlapFactor tag2) {
        for (OverlappingObject o1 : OverlapParts.get(tag1)) {
            for (OverlappingObject o2 : OverlapParts.get(tag2)) {
                if (overlaps(o1, o2)) {
                    o1.eventOnOverlap(o2);
                    o2.eventOnOverlap(o1);
                }
            }
        }
    }

    public OverlapManager() {
        for (OverlapFactor tag : OverlapFactor.values()) {
            Subscriptions.put(tag, new TaggedSmartSubscribing(tag));
            OverlapParts.put(tag, new CopyOnWriteArrayList<>());
        }
    }

    private static boolean overlaps(OverlappingObject o1, OverlappingObject o2) {
        float upperBound = o1.getOverlapLocation().y();
        float lowerBound = o1.getOverlapLocation().y() + o1.getOverlapBounds().height();
        float rightBound = o1.getOverlapLocation().x() + o1.getOverlapBounds().width();
        float leftBound = o1.getOverlapLocation().x();
        float other_upperBound = o2.getOverlapLocation().y();
        float other_lowerBound = o2.getOverlapLocation().y() + o2.getOverlapBounds().height();
        float other_rightBound = o2.getOverlapLocation().x() + o2.getOverlapBounds().width();
        float other_leftBound = o2.getOverlapLocation().x();

        boolean leftOverlap = leftBound < other_rightBound && leftBound >= other_leftBound;
        boolean rightOverlap = rightBound > other_leftBound && rightBound <= other_rightBound;
        boolean upperOverlap = upperBound < other_lowerBound && upperBound >= other_upperBound;
        boolean lowerOverlap = lowerBound > other_upperBound && lowerBound <= other_lowerBound;
        boolean xOverlap = leftOverlap || rightOverlap;
        boolean yOverlap = upperOverlap || lowerOverlap;

        return xOverlap && yOverlap;
    }

    private final Map<OverlapFactor, List<OverlappingObject>> OverlapParts = Collections.synchronizedMap(
            new EnumMap<>(OverlapFactor.class)
    );
    private final Map<OverlapFactor, TaggedSmartSubscribing> Subscriptions = Collections.synchronizedMap(
            new EnumMap<>(OverlapFactor.class)
    );

    private class TaggedSmartSubscribing extends SmartSubscribing<OverlappingObject> {

        public TaggedSmartSubscribing(OverlapFactor tag) {
            Tag = tag;
        }

        @Override
        protected void innerRegister(OverlappingObject object) {
            OverlapParts.get(Tag).add(object);
        }

        @Override
        public void onDeathCallback(OverlappingObject deadSubscriber) {
            OverlapParts.get(Tag).remove(deadSubscriber);
        }

        private final OverlapFactor Tag;
    }
}
