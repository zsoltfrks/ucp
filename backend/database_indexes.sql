-- Performance Indexes for UCP Database
-- Execute this script to create recommended indexes for optimal query performance

-- Users Table Indexes
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);
CREATE INDEX IF NOT EXISTS idx_users_active ON users(active);
CREATE INDEX IF NOT EXISTS idx_users_role_active ON users(role, active);

-- Player Profiles Table Indexes
CREATE INDEX IF NOT EXISTS idx_player_profiles_user_id ON player_profiles(user_id);
CREATE INDEX IF NOT EXISTS idx_player_profiles_level ON player_profiles(level);
CREATE INDEX IF NOT EXISTS idx_player_profiles_money ON player_profiles(money);
CREATE INDEX IF NOT EXISTS idx_player_profiles_last_login ON player_profiles(last_login);

-- Houses Table Indexes
CREATE INDEX IF NOT EXISTS idx_houses_owner_id ON houses(owner_id);
CREATE INDEX IF NOT EXISTS idx_houses_locked ON houses(locked);
CREATE INDEX IF NOT EXISTS idx_houses_purchased_at ON houses(purchased_at);
CREATE INDEX IF NOT EXISTS idx_houses_owner_locked ON houses(owner_id, locked);

-- Vehicles Table Indexes
CREATE INDEX IF NOT EXISTS idx_vehicles_owner_id ON vehicles(owner_id);
CREATE INDEX IF NOT EXISTS idx_vehicles_plate_number ON vehicles(plate_number);
CREATE INDEX IF NOT EXISTS idx_vehicles_impounded ON vehicles(impounded);
CREATE INDEX IF NOT EXISTS idx_vehicles_purchased_at ON vehicles(purchased_at);
CREATE INDEX IF NOT EXISTS idx_vehicles_owner_impounded ON vehicles(owner_id, impounded);

-- Bans Table Indexes
CREATE INDEX IF NOT EXISTS idx_bans_user_id ON bans(user_id);
CREATE INDEX IF NOT EXISTS idx_bans_active ON bans(active);
CREATE INDEX IF NOT EXISTS idx_bans_expires_at ON bans(expires_at);
CREATE INDEX IF NOT EXISTS idx_bans_user_active ON bans(user_id, active);
CREATE INDEX IF NOT EXISTS idx_bans_banned_at ON bans(banned_at);

-- Warnings Table Indexes
CREATE INDEX IF NOT EXISTS idx_warnings_user_id ON warnings(user_id);
CREATE INDEX IF NOT EXISTS idx_warnings_acknowledged ON warnings(acknowledged);
CREATE INDEX IF NOT EXISTS idx_warnings_user_acknowledged ON warnings(user_id, acknowledged);
CREATE INDEX IF NOT EXISTS idx_warnings_warned_at ON warnings(warned_at);

-- Admin Notes Table Indexes
CREATE INDEX IF NOT EXISTS idx_admin_notes_user_id ON admin_notes(user_id);
CREATE INDEX IF NOT EXISTS idx_admin_notes_created_at ON admin_notes(created_at);

-- Verify indexes were created
SELECT 
    schemaname,
    tablename,
    indexname,
    indexdef
FROM 
    pg_indexes
WHERE 
    tablename IN ('users', 'player_profiles', 'houses', 'vehicles', 'bans', 'warnings', 'admin_notes')
    AND schemaname = 'public'
ORDER BY 
    tablename, indexname;
