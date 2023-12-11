# Entropy Factions Integration
> Implements the new IsInCannonApi in Entropy version 1.1 and upwards using FactionsBridge.

This Plugin is currently only required if you want to use the patch "disable-non-full-block-hitbox" and require it to work with claims instead of distance from a cannon.
In the future other features might use the isInCannonApi as well.

### Dependencies:
- [FactionsBridge](https://www.spigotmc.org/resources/factionsbridge.89716/)

Note: While this implementation is optimised, using your own faction plugins api will always be faster than using factions bridge. I can not make an implementation for every factions plugin so I recommend to make your own version if you want to use this on a larger server or with a custom factions plugin.