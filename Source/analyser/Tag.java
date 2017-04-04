package analyser;

/**
 * The interface Tag contains constants that represent tags for words and
 * suffixes
 */
public interface Tag
{
String ENGLISH_ABBREVIATED = "English_Abbreviated";
String ENGLISH_EXPAND = "English_Expanded";
String TAMIL_ABBREVIATED = "Tamil_Abbreviated";
String TAMIL_EXPAND = "Tamil_Expanded";

/**
 * Tag for root word or suffix
 */
int Noun =								100;
int Pronoun =							101;
int InterrogativeNoun =					102;
int ObliquePronoun =					103;
int AdjNoun =							104;
int NonTamilNoun =						105;
int PronounCase =						106;
int PronounClitic =						107;
int Verb =								200;
int AuxVerb =							201;
int FiniteVerb =						202;
int NegFiniteVerb =						203;
int Adjective =							300;
int DemonstrativeAdjective =			301;
int InterrogativeAdj =					302;
int Adverb =							400;
int AccCase =							500;
int InsCase =							501;
int DatCase =							502;
int AssoCase =							503;
int LocCase =							504;
int AblCase =							505;
int GenCase =							506;
int AdverbialSuffix =					600;
int AdjectivalSuffix =					601;
int BoundPostposition =					602;
int ConjSuffix =						603;
int FiniteVerbSuffix =					604;
int NegFiniteVerbSuffix =				605;
int Plural =							700;
int SingularPlural =					701;
int PastTenseMarker =					800;
int PresentTenseMarker =				801;
int FutureTenseMarker =					802;
int VPSuffix =							900;
int Infinitive =						901;
int NegCondSuffix =						902;
int Conditional =						903;
int ImperativeSingular =				904;
int ImperativePlural =					905;
int PermSuffix =						906;
int ConcessiveSuffix =					907;
int FutureNeg =							908;
int NegVP =								909;
int ImpNeg_Prohibitive =				910;
int RPSuffix =							911;
int Interjection =						1000;
int ProNeuterPlural =					1100;
int ProThirdPersonMasSingular =			1101;
int ProThirdPersonFemSingular =			1102;
int ProThirdPersonMasFemSingular =		1103;
int ProThirdPersonPlural =				1104;
int ProNeuterSingular =					1105;
int Postposition =						1300;
int Conjunction =						1301;
int Particle =							1302;
int Intensifier =						1303;
int Sandhi =							1304;
int Oblique =							1305;
int Clitic =							1306;
int Euphonic =							1307;
int ParticleSuffix =					1308;
int Unknown =							1400;
int ThirdFutureNeuterSingular_RP =		1401;
int NeuterPlural_RP =					1402;//remove
int NeuterGenderSuffix =				2003;
int NeuterPlural =						2023;
int NeuterSingular =					2013;
int FirstPersonSingular =				2110;
int FirstPersonPlural =					2120;
int SecondPersonSingular =				2210;
int SecondPersonNeuterSingular =		2213;
int SecondPersonPlural =				2220;
int SecondPersonNeuterPlural =			2223;
int ThirdPersonSingular =				2310;
int ThirdPersonMasSingular =			2311;
int ThirdPersonFemSingular =			2312;
int ThirdPersonSingularNeuter =			2313;
int ThirdPersonMasFemSingular =			2314;
int ThirdPersonPlural =					2320;
}
