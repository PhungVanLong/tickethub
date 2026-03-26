import React from 'react'
import { globalStyles } from '../app/styles'

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string
  error?: string
  helperText?: string
}

export const Input: React.FC<InputProps> = ({
  label,
  error,
  helperText,
  className = '',
  ...props
}) => {
  const inputStyles = {
    width: '100%',
    padding: '8px 12px',
    border: `1px solid ${error ? globalStyles.colors.error : '#d1d5db'}`,
    borderRadius: globalStyles.borderRadius.md,
    fontSize: globalStyles.typography.fontSize.md,
    fontFamily: globalStyles.typography.fontFamily,
    transition: 'border-color 0.2s ease-in-out',
    outline: 'none',
    '&:focus': {
      borderColor: globalStyles.colors.primary,
      boxShadow: `0 0 0 3px rgba(59, 130, 246, 0.1)`
    }
  }

  const labelStyles = {
    display: 'block',
    marginBottom: '4px',
    fontSize: globalStyles.typography.fontSize.sm,
    fontWeight: 500,
    color: globalStyles.colors.text
  }

  const errorStyles = {
    color: globalStyles.colors.error,
    fontSize: globalStyles.typography.fontSize.xs,
    marginTop: '4px'
  }

  const helperTextStyles = {
    color: globalStyles.colors.textSecondary,
    fontSize: globalStyles.typography.fontSize.xs,
    marginTop: '4px'
  }

  return (
    <div className={className}>
      {label && (
        <label style={labelStyles}>
          {label}
        </label>
      )}
      <input
        style={inputStyles}
        {...props}
      />
      {error && (
        <div style={errorStyles}>
          {error}
        </div>
      )}
      {helperText && !error && (
        <div style={helperTextStyles}>
          {helperText}
        </div>
      )}
    </div>
  )
}
