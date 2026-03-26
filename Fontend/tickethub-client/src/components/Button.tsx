import React from 'react'
import { globalStyles } from '../app/styles'

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'outline' | 'ghost'
  size?: 'sm' | 'md' | 'lg'
  loading?: boolean
  children: React.ReactNode
}

export const Button: React.FC<ButtonProps> = ({
  variant = 'primary',
  size = 'md',
  loading = false,
  children,
  disabled,
  className = '',
  ...props
}) => {
  const baseStyles = {
    display: 'inline-flex',
    alignItems: 'center',
    justifyContent: 'center',
    border: 'none',
    borderRadius: globalStyles.borderRadius.md,
    fontFamily: globalStyles.typography.fontFamily,
    cursor: disabled || loading ? 'not-allowed' : 'pointer',
    transition: 'all 0.2s ease-in-out',
    opacity: disabled || loading ? 0.6 : 1,
    fontWeight: 500
  }

  const variantStyles = {
    primary: {
      backgroundColor: globalStyles.colors.primary,
      color: 'white',
      '&:hover': {
        backgroundColor: '#2563eb'
      }
    },
    secondary: {
      backgroundColor: globalStyles.colors.secondary,
      color: 'white',
      '&:hover': {
        backgroundColor: '#475569'
      }
    },
    outline: {
      backgroundColor: 'transparent',
      color: globalStyles.colors.primary,
      border: `1px solid ${globalStyles.colors.primary}`,
      '&:hover': {
        backgroundColor: globalStyles.colors.primary,
        color: 'white'
      }
    },
    ghost: {
      backgroundColor: 'transparent',
      color: globalStyles.colors.text,
      '&:hover': {
        backgroundColor: globalStyles.colors.surface
      }
    }
  }

  const sizeStyles = {
    sm: {
      padding: '6px 12px',
      fontSize: globalStyles.typography.fontSize.sm
    },
    md: {
      padding: '8px 16px',
      fontSize: globalStyles.typography.fontSize.md
    },
    lg: {
      padding: '12px 24px',
      fontSize: globalStyles.typography.fontSize.lg
    }
  }

  const styles = {
    ...baseStyles,
    ...variantStyles[variant],
    ...sizeStyles[size]
  }

  return (
    <button
      style={styles}
      disabled={disabled || loading}
      className={className}
      {...props}
    >
      {loading ? (
        <>
          <span
            style={{
              display: 'inline-block',
              width: '16px',
              height: '16px',
              border: '2px solid transparent',
              borderTop: `2px solid currentColor`,
              borderRadius: '50%',
              animation: 'spin 1s linear infinite',
              marginRight: '8px'
            }}
          />
          Loading...
        </>
      ) : (
        children
      )}
    </button>
  )
}
