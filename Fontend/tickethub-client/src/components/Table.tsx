import React from 'react'
import { globalStyles } from '../app/styles'

interface TableColumn {
  key: string
  title: string
  render?: (value: any, record: any) => React.ReactNode
}

interface TableProps {
  columns: TableColumn[]
  data: any[]
  loading?: boolean
  className?: string
}

export const Table: React.FC<TableProps> = ({
  columns,
  data,
  loading = false,
  className = ''
}) => {
  const tableStyles: React.CSSProperties = {
    width: '100%',
    borderCollapse: 'collapse' as const,
    fontSize: globalStyles.typography.fontSize.md,
    fontFamily: globalStyles.typography.fontFamily
  }

  const headerStyles = {
    backgroundColor: globalStyles.colors.surface,
    borderBottom: `1px solid #e5e7eb`,
    padding: '12px',
    textAlign: 'left' as const,
    fontWeight: 600,
    color: globalStyles.colors.text
  }

  const cellStyles = {
    padding: '12px',
    borderBottom: `1px solid #e5e7eb`,
    color: globalStyles.colors.text
  }

  const rowStyles: React.CSSProperties = {
    backgroundColor: 'transparent'
  }

  if (loading) {
    return (
      <div style={{ textAlign: 'center', padding: '40px' }}>
        <div>Loading...</div>
      </div>
    )
  }

  return (
    <div className={className}>
      <table style={tableStyles}>
        <thead>
          <tr>
            {columns.map((column) => (
              <th key={column.key} style={headerStyles}>
                {column.title}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((record, index) => (
            <tr key={index} style={rowStyles}>
              {columns.map((column) => (
                <td key={column.key} style={cellStyles}>
                  {column.render
                    ? column.render(record[column.key], record)
                    : record[column.key]}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
      {data.length === 0 && (
        <div style={{ textAlign: 'center', padding: '40px', color: globalStyles.colors.textSecondary }}>
          No data available
        </div>
      )}
    </div>
  )
}
